const mongoose = require('mongoose');

const organizationSchema = new mongoose.Schema({
  name: { type: String, required: true },
  type: { type: String, enum: ['Club', 'School', 'Organization'], required: true },
  address: String,
  password: { type: String, required: true },
  contactEmail: { type: String, required: true },
  teams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
  competitions: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Competition' }],
  followers: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }], // Users following the organization
  followingUsers: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }], // Users that the organization follows
  isVerified: { type: Boolean, default: false }, // Email verification status
  createdAt: { type: Date, default: Date.now }
});



module.exports = mongoose.model('Organization', organizationSchema);
