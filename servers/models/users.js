const mongoose = require('mongoose');

const userSchema = new mongoose.Schema({
  username: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  collegeId: { type: mongoose.Schema.Types.ObjectId, ref: 'Organization' }, // College the user belongs to
  followedOrganizations: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Organization' }] ,
  role: { type: String, enum: ['Athlete', 'Coach', 'Admin'], required: false}, // Different roles
  profile: {
    sport: String,
    age: Number,
    experience: Number, // in years
    achievements: [String], // List of achievements
    teams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }]
  },
  createdAt: { type: Date, default: Date.now }
});


module.exports = mongoose.model('User', userSchema);
