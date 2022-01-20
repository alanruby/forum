import React, { useState } from 'react';
import './Login.css';
import PropTypes from 'prop-types';
import { tryLogin } from '../../client';
import { Form, Input, Button, Checkbox } from 'antd';
import { useHistory } from 'react-router-dom';

async function loginUser(credentials) {
 return tryLogin(credentials)
}
export default function Login({ setToken }) {
  const [username, setUserName] = useState();
  const [password, setPassword] = useState();
  const history = useHistory();

  const handleSubmit = async e => {
    e.preventDefault();
    sessionStorage.setItem('username', username);
    const response = await loginUser({
      "username":username,
      "password":password
    });
   

    for (var pair of response.headers.entries()) {
      if (pair[0] == "authorization") {
        console.log(pair[0]+":"+pair[1]);
        setToken({'token':pair[1]});
        history.push("/Posting");
      }
    }
 } 



   return (
    <Form
      name="basic"
      labelCol={{ span: 4 }}
      wrapperCol={{ span: 8 }}
      initialValues={{ remember: true }}
      
      autoComplete="off"
      onSubmit={handleSubmit}
    >
      <Form.Item wrapperCol={{ offset: 4, span: 8 }}>
        <h2>Login</h2>
      </Form.Item>
      <Form.Item
        label="Username"
        name="username"
        rules={[{ required: true, message: 'Please input your username!' }]}
        onChange={e => setUserName(e.target.value)}
      >
        <Input />
      </Form.Item>

      <Form.Item
        label="Password"
        name="password"
        rules={[{ required: true, message: 'Please input your password!' }]}
        onChange={e => setPassword(e.target.value)}
      >
        <Input.Password />
      </Form.Item>

      

      <Form.Item wrapperCol={{ offset: 4, span: 8 }}>
        <Button type="primary" htmlType="submit" onClick={handleSubmit}>
          Submit
        </Button>
      </Form.Item>
    </Form>
  )
}

Login.propTypes = {
  setToken: PropTypes.func.isRequired
};