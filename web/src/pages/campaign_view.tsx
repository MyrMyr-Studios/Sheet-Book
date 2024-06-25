import axios from 'axios'
import { useState, useEffect } from "react";
import { Button, Navbar, Dropdown, Input } from 'react-daisyui';
import { useLocation, useNavigate, Link } from 'react-router-dom';

function CampaignView() {
  const location = useLocation();
  const navigate = useNavigate();
  const [user, setUser] = useState(location.state ? location.state.user : {name: null, id: -1})
  const [email, setEmail] = useState<string | null>(null)
  const [campaignName, setCampaignName] = useState<string | null>(null)
  const [campaignId, setCampaignId] = useState<number>(-1)
  const [userList, setUserList] = useState<any[]>([])
  const [sheetList, setSheetList] = useState<any[]>([])

  useEffect(() => {
    if (location.state && location.state.campaign) {
      setCampaignName(location.state.campaign.name)
      setCampaignId(location.state.campaign.campaignId)
      getCampaignInfo(location.state.campaign.campaignId)
    }
  }, [location])

  const getCampaignInfo = (id: number) => {
    axios
      .get('/campaign/info', {params: {id: id}})
      .then((response) => {
        if (response.status === 200) {
          setCampaignName(response.data.name)
          setUserList(response.data.users)
          setSheetList(response.data.sheets)
        }
      })
  }

  const createCampaign = () => {
    axios
      .post('/campaigns', {name: campaignName, campaignId: campaignId, userList: userList, sheetList: sheetList})
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

  const validateCampaignName = (name: string) => {
    const campaignNameInput = document.getElementById("campaignName")
    const campaignName_error = document.getElementById("campaignName_error")
    if(name === "") {
      campaignNameInput?.classList.add("input-error")
      if(campaignName_error) campaignName_error.innerText = "Campaign name cannot be empty"
    }
    else {
      campaignNameInput?.classList.remove("input-error")
      if(campaignName_error) campaignName_error.innerText = ""
    }
    setCampaignName(name)
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

  const deleteCampaign = () => {
    axios
      .get('/campaign/delete', {params: {id: campaignId}})
      .then((response) => {
        if (response.status === 200) {
          navigate('/campaigns', {state: {user: user}})
        }
      })
  }

  const addUser = () => {
    axios
      .post('/campaign/users', {id: campaignId, email: email})
      .then((response) => {
        if (response.status === 200) {
          getCampaignInfo(campaignId)
        }
      }).catch((error) => {
        if(error.response.status === 404) {
          const emailInput = document.getElementById("email")
          emailInput?.classList.add("input-error")
          const email_error = document.getElementById("email_error")
          if(email_error) email_error.innerText = "User does not exist"
        }
      })
  }

  const removeUser = (email: string) => {
    axios
      .get('/campaign/users', {params: {id: campaignId, email: email}})
      .then((response) => {
        if (response.status === 200) {
          getCampaignInfo(campaignId)
        }
      })
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
      <span className="text-2xl font-bold" style={{margin: "1rem"}}>Campaign: {campaignName}</span>
      <br />
      <Input type="email" id="email" placeholder="Email" style={{width: "20rem", marginLeft: "1rem"}} onChange={(e) => validateEmail(e.target.value)} />
      <label className="label text-error" style={{fontSize: "0.75rem", lineHeight: "0.1rem", marginLeft: "1rem"}} id='email_error'></label>
      
      <Button color="primary" style={{margin: "1rem"}} onClick={addUser}>Add User</Button>
      <Button color="primary" style={{margin: "1rem"}} onClick={() => navigate('/sheets/view', {state: {user: user, campaignId: campaignId}})}>Add Sheet</Button>
      <Button color="primary" style={{margin: "1rem"}} onClick={deleteCampaign}>Delete Campaign</Button>

      {userList.map((user) => {
        return (
          <div key={user.email} className="bg-secondary flex" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem", justifyContent: "space-between", alignItems: "center"}}>
            <span className="text-lg font-bold text-secondary-content">{user.name}</span>
            <Button tag="label" tabIndex={0} color="ghost" shape="circle" onClick={() => removeUser(user.email)}>
              <span className="material-icons text-primary-content" style={{lineHeight: "1rem"}}>person_remove</span>
            </Button>
          </div>
        );
      })}
      {sheetList.map((sheet) => {
        return (
          <Link to="/sheets/view" state={{user: user, sheet: sheet}} key={sheet.sheetId}>
            <div className="bg-accent" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem"}}>
              <span className="text-lg font-bold text-accent-content">{sheet.name}</span>
            </div>
          </Link>
        );
      })}
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

      {campaignId === -1 ? newCampaign : campaignView}

    </div>
  );          
}

export default CampaignView;