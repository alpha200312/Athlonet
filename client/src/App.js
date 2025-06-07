import React, { useState, useEffect } from 'react';
import AuthPage from './components/AuthPage';
import Dashboard from './components/Dashboard';
import AddCompetition from './components/AddCompetition';
import { API_BASE_URL } from './utils/api';

function App() {
  const [currentPage, setCurrentPage] = useState('login');
  const [token, setToken] = useState(localStorage.getItem('athlonet_token'));
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(false);
  const [competitions, setCompetitions] = useState([]);

  const [loginData, setLoginData] = useState({ contactEmail: '', password: '' });
  const [registerData, setRegisterData] = useState({
    name: '', type: '', address: '', contactEmail: '', password: ''
  });

  const [competitionData, setCompetitionData] = useState({
    name: '', description: '', sport: '', startDate: '', endDate: '',
    location: '', isPrivate: false, status: 'upcoming'
  });

  // Decode token when it exists
  useEffect(() => {
    if (token) {
      localStorage.setItem('athlonet_token', token);
      const decoded = parseJwt(token);
      if (decoded?.organization?.id) {
        setUser({ id: decoded.organization.id });
        setCurrentPage('dashboard');
        fetchCompetitions();
      }
    }
  }, [token]);

  const parseJwt = (token) => {
    try {
      return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
      return null;
    }
  };

  const handleLogin = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE_URL}/organization/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(loginData)
      });
      const data = await res.json();
      if (data.success) {
        setToken(data.token);
        setLoginData({ contactEmail: '', password: '' });
      } else {
        alert(data.msg || 'Login failed');
      }
    } catch (err) {
      alert('Login error: ' + err.message);
    }
    setLoading(false);
  };

  const handleRegister = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE_URL}/organization/add`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(registerData)
      });
      const data = await res.json();
      if (data.success) {
        setToken(data.token);
        setRegisterData({
          name: '', type: '', address: '', contactEmail: '', password: ''
        });
      } else {
        alert(data.msg || 'Registration failed');
      }
    } catch (err) {
      alert('Registration error: ' + err.message);
    }
    setLoading(false);
  };

  const fetchCompetitions = async () => {
    try {
      const res = await fetch(`${API_BASE_URL}/event/all`);
      const data = await res.json();
      if (data.events) {
        setCompetitions(data.events);
      }
    } catch (err) {
      console.error('Error fetching competitions:', err);
    }
  };

  const handleAddCompetition = async () => {
    setLoading(true);
    try {
      const res = await fetch(`${API_BASE_URL}/event/add`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({ ...competitionData, organizer: user.id })
      });
      const data = await res.json();
      if (res.ok) {
        alert('Event created successfully');
        fetchCompetitions();
        setCompetitionData({
          name: '', description: '', sport: '', startDate: '', endDate: '',
          location: '', isPrivate: false, status: 'upcoming'
        });
        setCurrentPage('dashboard');
      } else {
        alert(data.message || 'Event creation failed');
      }
    } catch (err) {
      alert('Error adding event: ' + err.message);
    }
    setLoading(false);
  };

  const handleLogout = () => {
    localStorage.removeItem('athlonet_token');
    setToken(null);
    setUser(null);
    setCurrentPage('login');
  };

  if (!token) {
    return (
      <AuthPage
        loginData={loginData} setLoginData={setLoginData}
        registerData={registerData} setRegisterData={setRegisterData}
        handleLogin={handleLogin} handleRegister={handleRegister}
        loading={loading}
      />
    );
  }

  if (currentPage === 'add-competition') {
    return (
      <AddCompetition
        competitionData={competitionData}
        setCompetitionData={setCompetitionData}
        handleAddCompetition={handleAddCompetition}
        loading={loading}
        setCurrentPage={setCurrentPage}
        handleLogout={handleLogout}
      />
    );
  }

  return (
    <Dashboard
      competitions={competitions}
      currentPage={currentPage}
      setCurrentPage={setCurrentPage}
      handleLogout={handleLogout}
    />
  );
}

export default App;
