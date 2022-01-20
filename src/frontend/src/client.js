import fetch from 'unfetch';
import { errorNotification } from './Notification';




const checkStatus = response => {
	console.log("TOKEN" + JSON.parse(sessionStorage.getItem('token')))
	if (response.ok) {
		
		return response;
	}
	const error = new Error(response.statusText);
	error.response = response;
	
	return Promise.reject(error);
}

export const tryLogin = credencials => 
	fetch("login", {
		headers: {'Content-Type':'application/json'}, method:'POST' , body: JSON.stringify(credencials)
		
		}
	
	).then(checkStatus);

export const trySignup = credencials => 
	fetch("api/v1/registration", {
		headers: {'Content-Type':'application/json'}, method:'POST' , body: JSON.stringify(credencials)
		
		}
	
	).then(checkStatus).catch(
		err =>  {
			console.log(err.response)
			err.response.json().then(
				res => {
				console.log(res);
				errorNotification(res.message)	
			});
	});

export const getPostings = 
	() => fetch ("api/v1/postings", {
		headers: {'Content-Type':'application/json', 'Authorization':sessionStorage.getItem('authorization')}, method:'GET' 
		
	})
.then(checkStatus);

export const getPostingsByUser = 
	() => fetch ("api/v1/postings/user", {
		headers: {'Content-Type':'application/json', 'Authorization':sessionStorage.getItem('authorization')}, method:'GET' 
		
	})
.then(checkStatus);

export const addNewPosting = posting => fetch ("api/v1/postings", {
		headers: {'Content-Type':'application/json', 'Authorization':sessionStorage.getItem('authorization')}, method:'POST',
		body: JSON.stringify(posting)
		 
		
	})

export const addSupport = posting => fetch ("api/v1/supports/"+posting, {
		headers: {'Content-Type':'application/json', 'Authorization':sessionStorage.getItem('authorization')}, method:'POST'
		 
		
	})
.then(checkStatus);
	
export const getAllStudents = () => fetch ("api/v1/students")
.then(checkStatus);

export const addNewStudent = student => 
	fetch("api/v1/students", {
		headers: {'Content-Type':'application/json'}, method:'POST' , body: JSON.stringify(student)
		
		}
	
	).then(checkStatus);