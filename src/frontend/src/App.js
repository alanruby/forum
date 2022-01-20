import React, { useState } from 'react';
import './App.css';

import Posting from './components/Posting/Posting';
import Login from './components/Login/Login';
import Signup from './components/Signup/Signup';

import { BrowserRouter, Route, Switch, Link } from 'react-router-dom';
import useToken from './useToken';

function App() {
  const [signup, setSignup] = useState(true);
  const { token, setToken } = useToken();

  return (
    <div className="wrapper">
      <BrowserRouter>
         <h1>
           <nav>
           {!token && signup && <Link to="/signup" setSignup={!signup} onClick={() => setSignup(!signup)}>Sign up</Link>}
           {!token && !signup && <Link to="/login" setSignup={!signup} onClick={() => setSignup(!signup)}>login</Link>}
            
          </nav>
        </h1>
        
         <Switch>
          
          <Route path="/posting">
            {token && <Posting />}
            {!token && <Login setToken={setToken} />}
          </Route>
          <Route path="/signup">
            {!token && <Signup />}
          </Route>
          <Route path="/">
            {!token && <Login setToken={setToken} />}
          </Route>

        </Switch>
      </BrowserRouter>
    </div>
  );
}

export default App;