import React from 'react';
import { Trophy, Calendar, Users, MapPin, Plus, LogOut, Home } from 'lucide-react';

const Dashboard = ({ competitions, currentPage, setCurrentPage, handleLogout }) => {
  const formatDate = (date) =>
    new Date(date).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });

  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header */}
      <header className="bg-white shadow-sm border-b">
        <div className="max-w-7xl mx-auto px-4 flex justify-between items-center h-16">
          <div className="flex items-center">
            <Trophy className="h-8 w-8 text-blue-600 mr-3" />
            <h1 className="text-2xl font-bold text-gray-900">Athlonet</h1>
          </div>
          <nav className="flex space-x-4">
            <button
              onClick={() => setCurrentPage('dashboard')}
              className={`flex items-center px-3 py-2 rounded-md text-sm font-medium ${
                currentPage === 'dashboard' ? 'bg-blue-100 text-blue-700' : 'text-gray-600 hover:text-gray-900'
              }`}
            >
              <Home className="h-4 w-4 mr-2" /> Dashboard
            </button>
            <button
              onClick={() => setCurrentPage('add-competition')}
              className="flex items-center px-3 py-2 rounded-md text-sm font-medium text-white bg-blue-600 hover:bg-blue-700"
            >
              <Plus className="h-4 w-4 mr-2" /> Add Event
            </button>
            <button
              onClick={handleLogout}
              className="flex items-center px-3 py-2 rounded-md text-sm font-medium text-red-600 hover:text-red-800"
            >
              <LogOut className="h-4 w-4 mr-2" /> Logout
            </button>
          </nav>
        </div>
      </header>

      {/* Body */}
      <main className="max-w-7xl mx-auto p-6">
        <h2 className="text-3xl font-bold text-gray-900 mb-6">Your Dashboard</h2>

        {/* Stats */}
        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
          <StatCard label="Total Events" value={competitions.length} icon={<Trophy className="h-8 w-8 text-yellow-600" />} />
          <StatCard label="Ongoing" value={competitions.filter(c => c.status === 'ongoing').length} icon={<Users className="h-8 w-8 text-blue-600" />} />
          <StatCard label="Upcoming" value={competitions.filter(c => c.status === 'upcoming').length} icon={<Calendar className="h-8 w-8 text-green-600" />} />
        </div>

        {/* Recent Events */}
        <div className="bg-white shadow-md rounded-lg">
          <div className="p-4 border-b font-semibold text-gray-800">Recent Events</div>
          <div className="divide-y">
            {competitions.slice(0, 5).map(event => (
              <div key={event._id} className="p-4 hover:bg-gray-50 transition">
                <h3 className="text-lg font-bold text-gray-900">{event.name}</h3>
                <p className="text-sm text-gray-600">{event.description}</p>
                <div className="text-sm text-gray-500 flex gap-4 mt-2">
                  <span className="flex items-center"><Trophy className="h-4 w-4 mr-1" /> {event.sport}</span>
                  <span className="flex items-center"><Calendar className="h-4 w-4 mr-1" /> {formatDate(event.startDate)}</span>
                  <span className="flex items-center"><MapPin className="h-4 w-4 mr-1" /> {event.location}</span>
                </div>
              </div>
            ))}
            {competitions.length === 0 && (
              <div className="p-4 text-gray-500 text-center">No events created yet. Start by adding one!</div>
            )}
          </div>
        </div>
      </main>
    </div>
  );
};

const StatCard = ({ label, value, icon }) => (
  <div className="bg-white shadow-md rounded-lg p-6 flex items-center">
    <div>{icon}</div>
    <div className="ml-4">
      <dt className="text-sm font-medium text-gray-500">{label}</dt>
      <dd className="text-2xl font-bold text-gray-900">{value}</dd>
    </div>
  </div>
);

export default Dashboard;
