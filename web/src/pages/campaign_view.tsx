import axios from 'axios'
import { useState, useEffect } from "react";
import { Button, Navbar, Dropdown, Input } from 'react-daisyui';
import { useLocation, useNavigate, Link } from 'react-router-dom';

function CampaignView() {
  const location = useLocation();
  const navigate = useNavigate();
  const [user, setUser] = useState(location.state ? location.state.user : {name: null, id: -1})
  const [campaign, setCampaign] = useState({campaignId: -1, name: null, userList: [], sheetList: []})

  useEffect(() => {
    if (location.state && location.state.campaign)
      setCampaign(location.state.campaign)
  }, [location])

  const createCampaign = () => {
    axios
      .post('/campaigns', campaign)
      .then((response) => {
        if (response.status === 201)
          navigate('/campaigns', {state: {user: user}})
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

  const validateCampaignName = (campaignName: string) => {
    const campaignNameInput = document.getElementById("campaignName")
    const campaignName_error = document.getElementById("campaignName_error")
    if(campaignName === "") {
      campaignNameInput?.classList.add("input-error")
      if(campaignName_error) campaignName_error.innerText = "Campaign name cannot be empty"
    }
    else {
      campaignNameInput?.classList.remove("input-error")
      if(campaignName_error) campaignName_error.innerText = ""
    }
    setCampaign({...campaign, name: campaignName})
  }

  const newCampaign = (
    <div className="flex gap-2" style={{flexDirection: "column", position: "absolute", top: "40%", left: "50%", transform: "translate(-50%, -50%)"}}>
      <img style={{height: "4.5rem", width: "4.5rem", alignSelf: "center"}} src="/sheet-book-outline-blue.png" />
      <div className="flex gap-2 bg-secondary" style={{flexDirection: "column", height: "20rem", width: "40rem", borderRadius: "1rem", padding: "3rem"}}>
          <label className="label text-secondary-content" style={{fontWeight: "700", lineHeight: "0.1rem"}}>Campaign Name</label>
          <Input type="text" onChange={(e) => validateCampaignName(e.target.value)} id='campaignName'/>
          <label className="label text-error" style={{fontSize: "0.75rem", lineHeight: "0.1rem"}} id='campaignName_error'></label>
          <Button className="btn btn-accent" style={{width: "50%", alignSelf: "center"}} onClick={createCampaign}>Create</Button>
          <Button className="btn btn-secondary" style={{width: "50%", alignSelf: "center"}} onClick={() => navigate('/campaigns', {state: {user: user}})}>Cancel</Button>
      </div>
    </div>
  )

  const campaignView = (
    <div>
      <span className="text-2xl font-bold ml-4 mt-4">Campaign: {campaign.name}</span>
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
              <Dropdown.Item>Settings</Dropdown.Item>
              <Dropdown.Item onClick={loggout}>Logout</Dropdown.Item>
            </Dropdown.Menu>
          </Dropdown>
        </div>   
      </Navbar>

      {campaign.campaignId === -1 ? newCampaign : campaignView}

    </div>
  );          
}

export default CampaignView;