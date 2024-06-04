import axios from 'axios'
import {useState, useEffect} from "react";
import {Input, Button} from 'react-daisyui'
import { themeChange } from 'theme-change';
import { Link } from 'react-router-dom';


const Register = () => {
  useEffect(() => {
    themeChange(false);
  }, []);
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [username, setUsername] = useState('')

  const register = () => {
    axios
      .post('/login', {name: username, email: email, password: password})
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data)
        }
      }, (error) => {
        console.log(error.request.response)
      })
  }

  return (
    <>
    <div className="flex gap-2" style={{flexDirection: "column", alignItems: "center", position: "absolute", top: "40%", left: "50%", transform: "translate(-50%, -50%)"}}>
        <h1 className="mb-10" style={{fontWeight: "700", fontSize: "2rem", lineHeight:"2.5rem"}}>Sheet Book</h1>
        <Input type="text" placeholder="Username" onChange={(e) => setUsername(e.target.value)}/>
        <Input type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)}/>
        <Input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/>
        <Button className="btn btn-primary" style={{width: "15rem"}} onClick={register}>Register</Button>
        <span className="text-primary">Already have an account? <Link to="/" className="text-secondary">Login</Link></span>
    </div>
    </>
  )
}

export default Register
