const teamSchema = new mongoose.Schema({
    name: { type: String, required: true },
    sport: { type: String, required: true },
    coach: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
    players: [{ type: mongoose.Schema.Types.ObjectId, ref: 'User' }],
    competitions: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Competition' }],
    createdAt: { type: Date, default: Date.now }
  });
  
  module.exports = mongoose.model('Team', teamSchema);
  