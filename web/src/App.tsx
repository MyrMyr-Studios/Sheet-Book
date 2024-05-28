import axios from 'axios'
import {useState} from "react";
import {Input, Button} from 'react-daisyui'
import './App.css'

axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.withCredentials = true

function App() {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [username, setUsername] = useState('')

  const login = () => {
    axios
      .get('/login', {params: {email: email, password: password}})
      .then((response) => {
        if (response.status === 200) {
          console.log(username)
          setUserList(response.data)
        }
      }, (error) => {
        setUserList([error.request.response])
      })
    }

  const register = () => {
    axios
      .post('/login', {name: username, email: email, password: password})
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data)
          setUserList(response.data)
        }
      }, (error) => {
        setUserList([error.request.response])
      })
  }
    

  const [userList, setUserList] = useState<any>([])
  const getUserList = () => {
    axios
      .get('/users')
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data)
          setUserList(JSON.stringify(response.data))
        }
      }, (error) => {
        setUserList([error.request.response])
      })
  }

  return (
    <>
      <h1 className="text-3xl font-bold mb-10">Sheet Book</h1>
      <div className="flex w-full component-preview p-2 items-center justify-center gap-2 font-sans">
        <Input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)}/>
      </div>
      <div className="flex w-full component-preview p-2 items-center justify-center gap-2 font-sans">
        <Input type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)}/>
      </div>
      <div className="flex w-full p-2 items-center justify-center gap-2">
        <Input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/>
      </div>
      <div className="flex w-full p-4 items-center justify-center gap-2">
        <Button className="btn btn-primary" onClick={login}>Login</Button>
        <Button className="btn btn-primary" onClick={register}>Register</Button>
      </div>
      <div className="flex w-full p-4 items-center justify-center gap-2">
        <Button className="btn btn-secondary" size="lg" onClick={getUserList}>Fetch data</Button>
      </div>
      <div className="mt-10">{userList}</div>
    </>
  )
}

export default App
