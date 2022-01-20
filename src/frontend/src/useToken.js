import { useState } from 'react';

export default function useToken() {
  const getToken = () => {
    const tokenString = sessionStorage.getItem('token');
    //const tokenString = localStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    return userToken?.token
  };

  const [token, setToken] = useState(getToken());

  const saveToken = userToken => {
    sessionStorage.setItem('token', JSON.stringify(userToken));
    sessionStorage.setItem('authorization', userToken.token);
   // localStorage.setItem('token', userToken.token)
    setToken(userToken.token);
  };

  return {
    setToken: saveToken,
    token
  }
}