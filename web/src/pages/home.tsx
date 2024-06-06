import axios from 'axios'
import {useState, useEffect} from "react";
import {Input, Button, Navbar, Dropdown, Badge} from 'react-daisyui'
import { themeChange } from 'theme-change';
import { Link } from 'react-router-dom';


function Home() {
  const [username, setUsername] = useState<string | null>(null)
  const [password, setPassword] = useState<string | null>(null)
  const [email, setEmail] = useState<string | null>(null)

  useEffect(() => {
    themeChange(false);
    getLoggedInUser();
  }, []);


  const getLoggedInUser = () => {
    axios
      .get('/user')
      .then((response) => {
        if (response.status === 200) setUsername(response.data)
      })
  }

  const login = () => {
    axios
      .get('/login', {params: {email: email, password: password}})
      .then((response) => {
        if (response.status === 200) getLoggedInUser();
      }).catch((error) => {
        if (error.response.status === 404) {
          const emailInput = document.getElementById("email")
          emailInput?.classList.add("input-error")
        }
        else if (error.response.status === 401) {
          const passwordInput = document.getElementById("password")
          passwordInput?.classList.add("input-error")
        }
      })
  }
  
  const loggout = () => {
    axios
      .get('/logout')
      .then((response) => {
        if (response.status === 200) setUsername(null)
      })
  }

  const validateLogin = () => {
    const emailInput = document.getElementById("email")
    const passwordInput = document.getElementById("password")
    if (!email || email == "")
      emailInput?.classList.add("input-error")
    else
      emailInput?.classList.remove("input-error")

    if (!password || password == "")
      passwordInput?.classList.add("input-error")
    else
      passwordInput?.classList.remove("input-error")

    if ((email && password) && (email != "" && password != ""))
      login();
  }
  
  const not_logged_dropdown = (
    <div className="flex-none">
      <span className="text-primary-content">Login</span>
      <Dropdown end>
        <Button tag="label" tabIndex={0} color="ghost" shape="circle">
          <span className="material-icons text-primary-content" style={{lineHeight: "1rem"}}>expand_more</span>
        </Button>
        <Dropdown.Menu className="mt-3 z-[1] w-52 menu-sm">
          <Input type="text" placeholder="Email" className="input input-bordered" style={{marginBottom: "0.5rem", width: "15rem"}} onChange={(e) => setEmail(e.target.value)} id='email'/>
          <Input type="password" placeholder="Password" className="input input-bordered" style={{marginBottom: "0.5rem", width: "15rem"}} onChange={(e) => setPassword(e.target.value)} id='password'/>
          <Button className="btn btn-primary" style={{width: "15rem", marginBottom: "0.5rem"}} onClick={validateLogin}>Login</Button>
          <Link to="/register">
            <Button className="btn btn-secondary" style={{width: "15rem"}}>Register</Button>
          </Link>
        </Dropdown.Menu>
      </Dropdown>
    </div>
  )

  const logged_dropdown = (
    <div className="flex-none">
      <span className="text-primary-content">{username}</span>
      <Dropdown end>
        <Button tag="label" tabIndex={0} color="ghost" shape="circle">
          <span className="material-icons text-primary-content" style={{lineHeight: "1rem"}}>expand_more</span>
        </Button>
        <Dropdown.Menu className="mt-3 z-[1] w-52 menu-sm">
          {/* <div className="flex items-center justify-center gap-2">
            <Button className="btn btn-primary" style={{width: "7rem"}} onClick={login}>Login</Button>
            <Button className="btn btn-secondary" style={{width: "7rem"}} onClick={register}>Register</Button>
          </div> */}

          {/* <hr style={{marginTop: ".5rem", marginBottom: ".5rem"}}/> */}
          <li>
            <a className="justify-between">
              Profile
              <Badge className="badge">New</Badge>
            </a>
          </li>
          <Dropdown.Item>Settings</Dropdown.Item>
          <Dropdown.Item onClick={loggout}>Logout</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </div>
  )

  return (
    <>
      <Navbar className='bg-primary shadow-xl justify-between' style={{padding: "1rem"}}>
        <div className="flex-none">
          <img style={{height: "3rem"}} src="/sheet-book-white.png" />
          <span className="text-primary-content" style={{paddingLeft: "1rem", fontWeight: "700", fontSize: "1.55rem"}}>Sheet Book</span>
        </div>
        {username ? logged_dropdown : not_logged_dropdown}
      </Navbar>
    </>
  )
}

export default Home