const mongoose = require('mongoose');

const competitionSchema = new mongoose.Schema({
  name: { type: String, required: true },
  description: { type: String },
  sport: { type: String, required: true },
  organizer: { type: mongoose.Schema.Types.ObjectId, ref: 'Organization', required: true },
  teams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
  participants: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }], // ✅ Players who joined
  startDate: { type: Date, required: true },
  endDate: { type: Date, required: true },
  location: { type: String },
  isPrivate: { type: Boolean, default: false },
  notifications: { type: Boolean, default: true },
  status: { type: String, enum: ['upcoming', 'ongoing', 'completed'], default: 'upcoming' },
  createdAt: { type: Date, default: Date.now },
});

module.exports = mongoose.model('Competition', competitionSchema);
