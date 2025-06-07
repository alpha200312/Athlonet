const express = require('express');
const router = express.Router();
const Organization = require('../models/organization');
const bcryptjs = require('bcryptjs');
const jwt = require('jsonwebtoken');

// Register a new organization
router.post('/add', async (req, res) => {
  const { name, type, address, contactEmail, password } = req.body;

  try {
    // Check if organization already exists
    const existingOrganization = await Organization.findOne({ contactEmail });
    if (existingOrganization) {
      return res.status(400).json({ success: false, msg: 'Organization already exists' });
    }

    // Hash the password
    const salt = await bcryptjs.genSalt(10);
    const hashedPassword = await bcryptjs.hash(password, salt);

    // Create new organization
    const newOrganization = new Organization({
      name,
      type,
      address,
      contactEmail,
      password: hashedPassword,
    });

    await newOrganization.save();

    // Generate JWT token
    const payload = {
      organization: { id: newOrganization.id },
    };

    jwt.sign(
      payload,
      process.env.jwtOrgSecret,
      { expiresIn: 360000 },
      (err, token) => {
        if (err) throw err;
        res.status(201).json({ success: true, msg: 'Organization registered successfully', token:token });
      }
    );
  } catch (error) {
    console.log(error.message);
    res.status(500).json({ success: false, msg: 'Server Error' });
  }
});

// Login an organization
router.post('/login', async (req, res) => {
  const { contactEmail, password } = req.body;

  try {
    // Check if organization exists
    const organization = await Organization.findOne({ contactEmail });
    if (!organization) {
      return res.status(400).json({ success: false, msg: 'Organization does not exist' });
    }

    // Check password
    const isMatch = await bcryptjs.compare(password, organization.password);
    if (!isMatch) {
      return res.status(400).json({ success: false, msg: 'Invalid password' });
    }

    // Generate JWT token
    const payload = {
      organization: { id: organization.id },
    };

    jwt.sign(
      payload,
      process.env.jwtOrgSecret,
      { expiresIn: 360000 },
      (err, token) => {
        if (err) throw err;
        res.status(200).json({ success: true, msg: 'Organization logged in', token:token });
      }
    );
  } catch (error) {
    console.log(error.message);
    res.status(500).json({ success: false, msg: 'Server Error' });
  }
});





// Get all organizations
router.get('/all', async (req, res) => {
  try {
    // Retrieve only verified organizations
    const organizations = await Organization.find({ isVerified: true });
    res.status(200).json({ success: true, organizations });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

  
  
  

module.exports = router;
