import axios from 'axios'
import {useState, useEffect} from "react";
import {Input, Button} from 'react-daisyui'
import { themeChange } from 'theme-change';
import { Link, useNavigate } from 'react-router-dom';


function Register() {
  useEffect(() => {
    themeChange(false);
  }, []);
  const [email, setEmail] = useState<string | null>(null)
  const [password, setPassword] = useState<string | null>(null)
  const [username, setUsername] = useState<string | null>(null)
  const navigate = useNavigate()

  const register = () => {
    axios
      .post('/login', {name: username, email: email, password: password})
      .then((response) => {
        if(response.status === 201) navigate('/')
      }).catch((error) => {
        if(error.response.status === 409) {
          const emailInput = document.getElementById("email")
          emailInput?.classList.add("input-error")
          const email_error = document.getElementById("email_error")
          if(email_error) email_error.innerText = "Email already in use"
        }
      })
  }

  const validateUsername = (username: string) => {
    const usernameInput = document.getElementById("username")
    const username_error = document.getElementById("username_error")
    if(username === "") {
      usernameInput?.classList.add("input-error")
      if(username_error) username_error.innerText = "Username cannot be empty"
    }
    else {
      usernameInput?.classList.remove("input-error")
      if(username_error) username_error.innerText = ""
    }
    setUsername(username)
  }

  const validateEmail = (email: string) => {
    const emailInput = document.getElementById("email")
    const email_error = document.getElementById("email_error")
    if(email === "") {
      emailInput?.classList.add("input-error")
      if(email_error) email_error.innerText = "Email cannot be empty"
    }
    else {
      emailInput?.classList.remove("input-error")
      if(email_error) email_error.innerText = ""
    }
    setEmail(email)
  }

  const validatePassword = (password: string) => {
    const passwordInput = document.getElementById("password")
    const password_error = document.getElementById("password_error")
    if(password === "") {
      passwordInput?.classList.add("input-error")
      if(password_error) password_error.innerText = "Password cannot be empty"
    }
    else {
      passwordInput?.classList.remove("input-error")
      if(password_error) password_error.innerText = ""
    }
    setPassword(password)
  }

  const validateUserInput = () => {
    validateEmail(email ? email : "")
    validatePassword(password ? password : "")
    validateUsername(username ? username : "")
    if(email && password && username)
      if(email !== "" && password !== "" && username !== "")
        register()
  }


  return (
    <>
    <div className="" style={{height: "100vh", width: "100vw", position: "absolute"}}></div>
    <div className="flex gap-2" style={{flexDirection: "column", position: "absolute", top: "40%", left: "50%", transform: "translate(-50%, -50%)"}}>
        <img style={{height: "4.5rem", width: "4.5rem", alignSelf: "center"}} src="/sheet-book-outline-blue.png" />
        <div className="flex gap-2 bg-secondary" style={{flexDirection: "column", height: "30rem", width: "40rem", borderRadius: "1rem", padding: "3rem"}}>
            <label className="label text-secondary-content" style={{fontWeight: "700", lineHeight: "0.1rem"}}>Username</label>
            <Input type="text" onChange={(e) => validateUsername(e.target.value)} id='username'/>
            <label className="label text-error" style={{fontSize: "0.75rem", lineHeight: "0.1rem"}} id='username_error'></label>
            <label className="label text-secondary-content" style={{fontWeight: "700", lineHeight: "0.1rem"}}>Email</label>
            <Input type="email" onChange={(e) => validateEmail(e.target.value)} id='email' />
            <label className="label text-error" style={{fontSize: "0.75rem", lineHeight: "0.1rem"}} id='email_error'></label>
            <label className="label text-secondary-content" style={{fontWeight: "700", lineHeight: "0.1rem"}}>Password</label>
            <Input type="password" onChange={(e) => validatePassword(e.target.value)} id='password'/>
            <label className="label text-error" style={{fontSize: "0.75rem", lineHeight: "0.1rem"}} id='password_error'></label>
            <Button className="btn btn-accent" style={{width: "50%", alignSelf: "center"}} onClick={validateUserInput}>Register</Button>
            <span className="text-secondary-content" style={{alignSelf: "center"}}>Already have an account? <Link to="/" className="text-accent">Login</Link></span>
        </div>
    </div>
    </>
  )
}

export default Register