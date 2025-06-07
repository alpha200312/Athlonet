import React, { useState } from 'react';
import { Trophy } from 'lucide-react';

const AuthPage = ({
  loginData, setLoginData,
  registerData, setRegisterData,
  handleLogin, handleRegister,
  loading
}) => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 via-purple-600 to-indigo-800 flex items-center justify-center p-4">
      <div className="bg-white/10 backdrop-blur-lg rounded-3xl p-8 w-full max-w-md border border-white/20 shadow-2xl">
        {/* Header */}
        <div className="text-center mb-8">
          <div className="flex items-center justify-center mb-4">
            <Trophy className="h-12 w-12 text-yellow-400 mr-2" />
            <h1 className="text-4xl font-bold text-white">Athlonet</h1>
          </div>
          <p className="text-white/80">Organize. Compete. Excel.</p>
        </div>

        {/* Tabs */}
        <div className="flex bg-white/20 rounded-xl p-1 mb-6">
          <button
            onClick={() => setIsLogin(true)}
            className={`flex-1 py-2 px-4 rounded-lg ${isLogin ? 'bg-white text-gray-900' : 'text-white hover:bg-white/10'}`}
          >
            Login
          </button>
          <button
            onClick={() => setIsLogin(false)}
            className={`flex-1 py-2 px-4 rounded-lg ${!isLogin ? 'bg-white text-gray-900' : 'text-white hover:bg-white/10'}`}
          >
            Register
          </button>
        </div>

        {/* Login Form */}
        {isLogin ? (
          <div className="space-y-4">
            <input
              type="email"
              placeholder="Contact Email"
              value={loginData.contactEmail}
              onChange={(e) => setLoginData({ ...loginData, contactEmail: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60"
            />
            <input
              type="password"
              placeholder="Password"
              value={loginData.password}
              onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60"
            />
            <button
              onClick={handleLogin}
              disabled={loading}
              className="w-full py-3 bg-gradient-to-r from-yellow-400 to-orange-500 text-white font-semibold rounded-xl hover:from-yellow-500 hover:to-orange-600 disabled:opacity-50"
            >
              {loading ? 'Logging in...' : 'Login'}
            </button>
          </div>
        ) : (
          // Register Form
          <div className="space-y-4">
            <input
              type="text"
              placeholder="Organization Name"
              value={registerData.name}
              onChange={(e) => setRegisterData({ ...registerData, name: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60"
            />
            <select
              value={registerData.type}
              onChange={(e) => setRegisterData({ ...registerData, type: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30"
            >
              <option value="">Select Type</option>
              <option value="Club">Sports Club</option>
              <option value="School">School</option>
              <option value="College">College</option>
              <option value="Corporate">Corporate</option>
              <option value="Community">Community</option>
            </select>
            <textarea
              placeholder="Address"
              value={registerData.address}
              onChange={(e) => setRegisterData({ ...registerData, address: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60 resize-none"
              rows="2"
            />
            <input
              type="email"
              placeholder="Contact Email"
              value={registerData.contactEmail}
              onChange={(e) => setRegisterData({ ...registerData, contactEmail: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60"
            />
            <input
              type="password"
              placeholder="Password"
              value={registerData.password}
              onChange={(e) => setRegisterData({ ...registerData, password: e.target.value })}
              className="w-full p-3 rounded-xl bg-white/20 text-white border border-white/30 placeholder-white/60"
            />
            <button
              onClick={handleRegister}
              disabled={loading}
              className="w-full py-3 bg-gradient-to-r from-green-400 to-blue-500 text-white font-semibold rounded-xl hover:from-green-500 hover:to-blue-600 disabled:opacity-50"
            >
              {loading ? 'Registering...' : 'Register'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default AuthPage;
