import fetch from 'unfetch';
const checkStatus = response => {
	console.log("11111" + localStorage.getItem('authorization'))
	if (response.ok) {
		
		return response;
	}
	console.log("Processando error.");
	const error = new Error(response.statusText);
	error.response = response;
	
	return Promise.reject(error);
}
	
export const getAllStudents = () => fetch ("api/v1/students")
.then(checkStatus);

export const addNewStudent = student => 
	fetch("api/v1/students", {
		headers: {'Content-Type':'application/json'}, method:'POST' , body: JSON.stringify(student)
		
		}
	
	).then(checkStatus);
	
export const login = () => 
	fetch("login", {
		headers: {'Content-Type':'application/json'}, method:'POST' , body: JSON.stringify({username:"alan",password:"aa"})
		
		}
	
	).then(checkStatus);
	

export const getTeste = () => 
	fetch("management/api/v1/students", {
		headers: {'Content-Type':'application/json','Authorization':localStorage.getItem('authorization')}, method:'GET'
		
		}
	
	).then(checkStatus);
	
	
	
export const deleteStudent = studentId =>
	fetch(`api/v1/students/`+studentId, {method:'DELETE'}).then(checkStatus);
	
