const roadmapSchema = new mongoose.Schema({
    user: { type: mongoose.Schema.Types.ObjectId, ref: 'User', required: true },
    milestones: [
      {
        title: String,
        description: String,
        dateAchieved: Date,
        goal: Boolean // Whether it's a goal or an achievement
      }
    ],
    createdAt: { type: Date, default: Date.now }
  });
  
  module.exports = mongoose.model('Roadmap', roadmapSchema);
  