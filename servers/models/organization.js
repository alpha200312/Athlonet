const organizationSchema = new mongoose.Schema({
    name: { type: String, required: true },
    type: { type: String, enum: ['Club', 'School', 'Organization'], required: true },
    address: String,
    contactEmail: { type: String, required: true },
    teams: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Team' }],
    competitions: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Competition' }],
    createdAt: { type: Date, default: Date.now }
  });
  
  module.exports = mongoose.model('Organization', organizationSchema);
  