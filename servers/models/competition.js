const competitionSchema = new mongoose.Schema({
    name: { type: String, required: true },
    sport: { type: String, required: true },
    teams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
    startDate: { type: Date, required: true },
    endDate: { type: Date, required: true },
    location: String,
    organizer: { type: mongoose.Schema.Types.ObjectId, ref: 'Organization' }, // Who organizes the event
    createdAt: { type: Date, default: Date.now }
  });
  
  module.exports = mongoose.model('Competition', competitionSchema);
  