import axios from 'axios'
import { useState, useEffect } from "react";
import { Button, Navbar, Dropdown } from 'react-daisyui';
import { Link, useLocation, useNavigate } from 'react-router-dom';

function Sheets() {
  const location = useLocation();
  const navigate = useNavigate();
  const [sheetList, setSheetList] = useState<any[]>([]);
  const [user, setUser] = useState(location.state ? location.state.user : {name: null, id: -1})

  useEffect(() => {
    getSheetList();
  }, []);

  const getSheetList = () => {
    axios
      .get('/sheets')
      .then((response) => {
        if (response.status === 200) setSheetList(response.data)
      })
  }

  const loggout = () => {
    axios
      .get('/logout')
      .then((response) => {
        if (response.status === 200) {
          setUser({name: null, id: -1})
          navigate('/')
        }
      })
  }

  return (
    <div>
      <Navbar className='bg-primary shadow-xl justify-between' style={{padding: "1rem"}}>
        <div className="flex-none gap-2">
          <Button color='ghost' className="text-primary-content" style={{padding: "0", fontWeight: "700", fontSize: "1.65rem", alignContent: "center"}} onClick={() => navigate('/')}>
            <img style={{height: "3rem", marginRight: "0.25rem"}} src="/icon.svg" />
            Sheet Book
          </Button>
          <Button color='ghost' className="text-primary-content" style={{padding: "0", marginLeft: "1rem", fontWeight: "500", fontSize: "1.25rem", paddingTop: "0.2rem"}} onClick={() => navigate('/sheets', {state: {user: user}})}>Sheets</Button>
          <Button color='ghost' className="text-primary-content" style={{padding: "0", marginLeft: "1rem", fontWeight: "500", fontSize: "1.25rem", paddingTop: "0.2rem"}} onClick={() => navigate('/campaigns', {state: {user: user}})}>Campaigns</Button>
        </div>  
        <div className="flex-none">
          <span className="text-primary-content">{user.name}</span>
          <Dropdown end>
            <Button tag="label" tabIndex={0} color="ghost" shape="circle">
              <span className="material-icons text-primary-content" style={{lineHeight: "1rem"}}>expand_more</span>
            </Button>
            <Dropdown.Menu className="mt-3 z-[1] w-52 menu-sm">
              <Dropdown.Item onClick={() => navigate('/sheets', {state: {user: user}})}>Sheets</Dropdown.Item>
              <Dropdown.Item onClick={() => navigate('/campaigns', {state: {user: user}})}>Campaigns</Dropdown.Item>
              <hr style={{marginTop: ".5rem", marginBottom: ".5rem"}}/>
              <Dropdown.Item onClick={() => navigate('/profile', {state: {user: user}})}>Profile</Dropdown.Item>
              <Dropdown.Item onClick={loggout}>Logout</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </div>   
      </Navbar>

      <Button color="primary" style={{margin: "1rem"}} onClick={() => navigate('/sheets/view', {state: {user: user}})}>Create Sheet</Button>

      <div>
        {sheetList.map((sheet) => {
          return (
            <Link to="/sheets/view" state={{user : user, sheet : sheet}} key={sheet.sheetId}>
              <div className="bg-accent flex" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem", height: "5rem", alignItems: "center"}}>
                <span className='text-secondary-content' style={{fontWeight: "bold", fontSize: "1.25rem"}}>{sheet.name} [ level {sheet.level} ]</span>
              </div>
            </Link>
          );
        })}
      </div>
    </div>
  );
}

export default Sheets;