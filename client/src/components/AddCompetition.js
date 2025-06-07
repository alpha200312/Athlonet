import React from 'react';
import { Plus, LogOut, Home } from 'lucide-react';

const AddCompetition = ({
  competitionData,
  setCompetitionData,
  handleAddCompetition,
  loading,
  setCurrentPage,
  handleLogout,
}) => {
  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 flex justify-between items-center h-16">
          <h1 className="text-2xl font-bold text-gray-900">Athlonet</h1>
          <nav className="flex space-x-4">
            <button
              onClick={() => setCurrentPage('dashboard')}
              className="text-gray-600 hover:text-gray-900 flex items-center"
            >
              <Home className="h-4 w-4 mr-2" />
              Dashboard
            </button>
            <button
              onClick={handleLogout}
              className="text-red-600 hover:text-red-800 flex items-center"
            >
              <LogOut className="h-4 w-4 mr-2" />
              Logout
            </button>
          </nav>
        </div>
      </header>

      {/* Form */}
      <main className="max-w-4xl mx-auto py-8 px-4">
        <h2 className="text-3xl font-bold text-gray-900 mb-6">Create New Event</h2>

        <div className="bg-white p-6 rounded-xl shadow space-y-6">
          <input
            type="text"
            placeholder="Event Name *"
            value={competitionData.name}
            onChange={(e) => setCompetitionData({ ...competitionData, name: e.target.value })}
            className="w-full p-3 border border-gray-300 rounded"
          />

          <select
            value={competitionData.sport}
            onChange={(e) => setCompetitionData({ ...competitionData, sport: e.target.value })}
            className="w-full p-3 border border-gray-300 rounded"
          >
            <option value="">Select Sport</option>
            <option value="Football">Football</option>
            <option value="Basketball">Basketball</option>
            <option value="Cricket">Cricket</option>
            <option value="Tennis">Tennis</option>
            <option value="Swimming">Swimming</option>
            <option value="Athletics">Athletics</option>
            <option value="Badminton">Badminton</option>
            <option value="Volleyball">Volleyball</option>
            <option value="Other">Other</option>
          </select>

          <textarea
            placeholder="Description"
            value={competitionData.description}
            onChange={(e) => setCompetitionData({ ...competitionData, description: e.target.value })}
            className="w-full p-3 border border-gray-300 rounded resize-none"
            rows="3"
          />

          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <input
              type="datetime-local"
              value={competitionData.startDate}
              onChange={(e) => setCompetitionData({ ...competitionData, startDate: e.target.value })}
              className="w-full p-3 border border-gray-300 rounded"
            />
            <input
              type="datetime-local"
              value={competitionData.endDate}
              onChange={(e) => setCompetitionData({ ...competitionData, endDate: e.target.value })}
              className="w-full p-3 border border-gray-300 rounded"
            />
          </div>

          <input
            type="text"
            placeholder="Location"
            value={competitionData.location}
            onChange={(e) => setCompetitionData({ ...competitionData, location: e.target.value })}
            className="w-full p-3 border border-gray-300 rounded"
          />

          <div className="flex items-center gap-2">
            <input
              type="checkbox"
              checked={competitionData.isPrivate}
              onChange={(e) => setCompetitionData({ ...competitionData, isPrivate: e.target.checked })}
              className="h-4 w-4 text-blue-600 border-gray-300 rounded"
            />
            <label className="text-sm text-gray-700">Private Event (Invitation only)</label>
          </div>

          <div className="flex justify-end gap-4">
            <button
              type="button"
              onClick={() => setCurrentPage('dashboard')}
              className="px-6 py-2 border border-gray-300 rounded text-gray-600 hover:bg-gray-100"
            >
              Cancel
            </button>
            <button
              type="button"
              onClick={handleAddCompetition}
              disabled={loading}
              className="px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 flex items-center"
            >
              {loading ? (
                <>
                  <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2"></div>
                  Creating...
                </>
              ) : (
                <>
                  <Plus className="h-4 w-4 mr-2" />
                  Create Event
                </>
              )}
            </button>
          </div>
        </div>
      </main>
    </div>
  );
};

export default AddCompetition;
