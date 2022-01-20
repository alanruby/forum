import React, { useState } from 'react';
import './Signup.css';
import { trySignup } from '../../client';
import { Form, Input, Button } from 'antd';
import { useHistory } from 'react-router-dom';
import { successNotification } from '../../Notification';



async function signupUser(credentials) {
 return trySignup(credentials)
}
//{username:"alan",password:"a"}
export default function Signup() {
  const history = useHistory();

  const [username, setUserName] = useState();
  const [password, setPassword] = useState();

  const handleSubmit = async e => {
    e.preventDefault();
    const response = await signupUser({
      "username":username,
      "password":password
    });

    if (response) {
      successNotification("User created!","You can login now ;)")
      history.push("/");
      console.log(response);
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
        <h2>Sign-up</h2>
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
