import axios from 'axios'
import {useState, useEffect} from "react";
import {Input, Button, Navbar, Dropdown, Badge} from 'react-daisyui'
import { themeChange } from 'theme-change';
import { Link } from 'react-router-dom';


const Home = () => {
  useEffect(() => {
    themeChange(false);
    getLoggedInUser();
  }, []);
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [username, setUsername] = useState('')

  const getLoggedInUser = () => {
    axios
      .get('/users')
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data)
        }
      }, (error) => {
        console.log(error.request.response)
      })
  }

  const login = () => {
    axios
      .get('/login', {params: {email: email, password: password}})
      .then((response) => {
        if (response.status === 200) {
          console.log(username)
        }
      }, (error) => {
        console.log(error.request.response)
      })
    }

  return (
    <>
      <Navbar className='bg-primary shadow-xl justify-between' style={{padding: "1rem"}}>
        <div className="flex-none">
          <img style={{height: "3rem"}} src="/sheet-book-white.png" />
          <span className="text-primary-content" style={{paddingLeft: "1rem", fontWeight: "700", fontSize: "1.55rem"}}>Sheet Book</span>
        </div>
        <div className="flex-none">
          <span className="text-primary-content" style={{paddingRight: ".5rem"}}>{username}</span>
          <Dropdown end>
            <Button tag="label" tabIndex={0} color="ghost" className="avatar" shape="circle">
              <div className="w-10 rounded-full">
                <img src="/sheet-book-outline-blue.png" />
              </div>
            </Button>
            <Dropdown.Menu className="mt-3 z-[1] w-52 menu-sm">
              <Input type="text" placeholder="Email" className="input input-bordered" style={{marginBottom: "0.5rem", width: "15rem"}} onChange={(e) => setEmail(e.target.value)} />
              <Input type="password" placeholder="Password" className="input input-bordered" style={{marginBottom: "0.5rem", width: "15rem"}} onChange={(e) => setPassword(e.target.value)} />
              <Button className="btn btn-primary" style={{width: "15rem", marginBottom: "0.5rem"}} onClick={login}>Login</Button>
              <Link to="/register">
                <Button className="btn btn-secondary" style={{width: "15rem"}}>Register</Button>
              </Link>
              {/* <div className="flex items-center justify-center gap-2">
                <Button className="btn btn-primary" style={{width: "7rem"}} onClick={login}>Login</Button>
                <Button className="btn btn-secondary" style={{width: "7rem"}} onClick={register}>Register</Button>
              </div> */}

              {/* <hr style={{marginTop: ".5rem", marginBottom: ".5rem"}}/>
              <li>
                <a className="justify-between">
                  Profile
                  <Badge className="badge">New</Badge>
                </a>
              </li>
              <Dropdown.Item>Settings</Dropdown.Item>
              <Dropdown.Item>Logout</Dropdown.Item> */}
            </Dropdown.Menu>
          </Dropdown>
        </div>
      </Navbar>
    </>
  )
}

export default Home
