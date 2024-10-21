const matchSchema = new mongoose.Schema({
    competition: { type: mongoose.Schema.Types.ObjectId, ref: 'Competition', required: true },
    teamA: { type: mongoose.Schema.Types.ObjectId, ref: 'Team', required: true },
    teamB: { type: mongoose.Schema.Types.ObjectId, ref: 'Team', required: true },
    date: { type: Date, required: true },
    score: {
      teamA: { type: Number, default: 0 },
      teamB: { type: Number, default: 0 }
    },
    winner: { type: mongoose.Schema.Types.ObjectId, ref: 'Team' }, // Reference to the winning team
    createdAt: { type: Date, default: Date.now }
  });
  
  module.exports = mongoose.model('Match', matchSchema);
  