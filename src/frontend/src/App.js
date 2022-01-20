import { Button, Radio } from "antd";
import './App.css';

import { getAllStudents, login, getTeste } from "./client";
import { useState, useEffect } from 'react';
import {errorNotification, successNotification} from "./Notification";


const testarGet = () => {
	getTeste().then(res => res.json()).then(console.log)
}

function App() {
	
 //const [trySignup,seTtrySignup] = useState(false);
 const [trylogin,setTrylogin] = useState(false);
 const tentar_login = () => testarGet()
	.then(res => res.json())
	.then(data => {
		    console.log("lendo dados")
			console.log(data);
			//setStudents(data);
		}	
	).catch(
		err =>  {
			console.log("aaaa123")
			err.response.json().then(
				res => {
				console.log(res);
				errorNotification("There was an issuem", res.message+" Status code:"+res.status+" error:"+res.error)	
			});
		}).finally( () => setTrylogin(true) );
console.log("Tentantdo "+trylogin)


useEffect( () => {
		console.log("component is mounted");
		tentar_login();
	},[]);


  //getAllStudents().then(res => res.json())
	//.then(console.log)	
	
/*login().then((response) => {
    for (var pair of response.headers.entries()) {
	
	  if (pair[0] == "authorization") {
		 localStorage.setItem('authorization',pair[1])
	  }
    }
  })	*/
  
  
  
 
  return (
    <div className="App">
    	<Button type='primary'>Hello</Button>
    	<br/>
    	<Radio.Group value='large'>
          <Radio.Button value="large">Large</Radio.Button>
          <Radio.Button value="default">Default</Radio.Button>
          <Radio.Button  onClick={() => testarGet()} value="small">Small</Radio.Button>
        </Radio.Group>
    </div>
  );
}

export default App;
