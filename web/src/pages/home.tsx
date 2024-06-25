import axios from 'axios'
import { useState, useEffect } from "react";
import { Input, Button, Navbar, Dropdown } from 'react-daisyui'
import { themeChange } from 'theme-change';
import { Link, useNavigate } from 'react-router-dom';

function Home() {
  const [user, setUser] = useState({name: null, id: -1})
  const [password, setPassword] = useState<string | null>(null)
  const [email, setEmail] = useState<string | null>(null)
  const navigate = useNavigate()

  useEffect(() => {
    themeChange(false);
    getLoggedInUser();
  }, []);

  const getLoggedInUser = () => {
    axios
      .get('/user')
      .then((response) => {
        if (response.status === 200) setUser(response.data)
      })
  }

  const login = () => {
    axios
      .get('/login', {params: {email: email, password: password}})
      .then((response) => {
        if (response.status === 200) {
          setUser(response.data)
          document.getElementById("userDropdown")?.classList.remove("dropdown-open")
        }
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
        if (response.status === 200) setUser({name: null, id: -1})
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
      <Dropdown id='userDropdown' end>
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
      <span className="text-primary-content">{user.name}</span>
      <Dropdown id='userDropdown' end>
        <Button tag="label" tabIndex={0} color="ghost" shape="circle">
          <span className="material-icons text-primary-content" style={{lineHeight: "1rem"}}>expand_more</span>
        </Button>
        <Dropdown.Menu className="mt-3 z-[1] w-52 menu-sm">
          <Dropdown.Item onClick={() => navigate('/sheets', {state: {user: user}})}>Sheets</Dropdown.Item>
          <Dropdown.Item onClick={() => navigate('/campaigns', {state: {user: user}})}>Campaigns</Dropdown.Item>
          <hr style={{marginTop: ".5rem", marginBottom: ".5rem"}}/>
          <Dropdown.Item onClick={() => navigate('/profile', {state: {user: user}})}>Profile</Dropdown.Item>
          <Dropdown.Item>Settings</Dropdown.Item>
          <Dropdown.Item onClick={loggout}>Logout</Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </div>
  )

  return (
    <div>
      <Navbar className='bg-primary shadow-xl justify-between' style={{padding: "1rem"}}>
        <div className="flex-none">
          <Button color='ghost' className="text-primary-content" style={{padding: "0", fontWeight: "700", fontSize: "1.65rem", alignContent: "center"}} onClick={() => navigate('/')}>
            <img style={{height: "3rem", marginRight: "0.25rem"}} src="/icon.svg" />
            Sheet Book
          </Button>
          <Button color='ghost' className="text-primary-content" style={{padding: "0", marginLeft: "1rem", fontWeight: "500", fontSize: "1.25rem", paddingTop: "0.2rem"}} onClick={() => user.name ? navigate('/sheets', {state: {user: user}}) : document.getElementById("userDropdown")?.classList.toggle("dropdown-open")}>Sheets</Button>
          <Button color='ghost' className="text-primary-content" style={{padding: "0", marginLeft: "1rem", fontWeight: "500", fontSize: "1.25rem", paddingTop: "0.2rem"}} onClick={() => user.name ? navigate('/campaigns', {state: {user: user}}) : document.getElementById("userDropdown")?.classList.toggle("dropdown-open")}>Campaigns</Button>
        </div>
        {user.name ? logged_dropdown : not_logged_dropdown}
      </Navbar>
      <div className={user.name ? "flex gap-2 bg-primary" : "hidden"} style={{flexDirection: "column", position: "absolute", top: "40%", left: "50%", transform: "translate(-50%, -50%)", borderRadius: "1rem", padding: "3rem"}}>
          <Button className="btn btn-secondary" style={{width: "15rem"}} onClick={() => navigate('/sheets/view', {state: {user: user}})}>Create Sheet</Button>
          <Button className="btn btn-secondary" style={{width: "15rem"}} onClick={() => navigate('/campaigns/view', {state: {user: user}})}>Create Campaign</Button>
          <Button className="btn btn-secondary" style={{width: "15rem"}} onClick={() => navigate('/sheets', {state: {user: user}})}>My Sheets</Button>
          <Button className="btn btn-secondary" style={{width: "15rem"}} onClick={() => navigate('/campaigns', {state: {user: user}})}>My Campaigns</Button>
      </div>
    </div>
  )
}

export default Home