import axios from 'axios'
import { useState, useEffect } from "react";
import { Button, Input, Textarea, Navbar, Dropdown } from 'react-daisyui';
import { useLocation, useNavigate } from 'react-router-dom';

function SheetEdit() {
  const [sheet, setSheet] = useState({
    ownerId: -1,
    sheetId: -1,
    campaignId: null,
    name: "",
    level: 0,
    class_t: "",
    background : "",
    race: "",
    alignment: "",
    xp: 0,
    strength: 0,
    dexterity: 0,
    constitution: 0,
    intelligence: 0,
    wisdom: 0,
    charisma: 0,
    inspiration: "",
    proficiencyPoints: 0,
    armorClass: 0,
    initiative: 0,
    speed: 0,
    personalityTraits: "",
    ideals: "",
    bonds: "",
    flaws: "",
    features: "",
    traits: "",
    equipment: "",
    proficiencies: "",
    perception: "",
    languages: "",
    hp: 0,
    temporaryHp: 0,
    hitDice: "",
    deathSaves: "",
    attacks: "",
    spellcasting: "",
    skills: "",
    savingThrows: ""
  })
  const location = useLocation();
  const navigate = useNavigate();
  const [user, setUser] = useState(location.state ? location.state.user : {name: null, id: -1})
  const [campaignList, setCampaignList] = useState<any[]>([]);
  const [campaignName, setCampaignName] = useState<string | null>(null)

  useEffect(() => {
    if (location.state) {
      if (location.state.sheet) {
        setSheet(location.state.sheet)
        getCampaignName(location.state.sheet.campaignId)
      }
      else if (location.state.campaignId)
        setCampaign(location.state.campaignId)
      getCampaignList();
    }
  }, [location])

  const getCampaignList = () => {
    axios
      .get('/campaigns')
      .then((response) => {
        if (response.status === 200) setCampaignList(response.data)
      })
  }

  const setCampaign = (id: any) => {
    setSheet({...sheet, campaignId: id})
    if (id === null || id === -1)
      setCampaignName("Selecionar campanha")
    else
      getCampaignName(id)
  }

  const getCampaignName = (id: any) => {
    if (id === null || id === -1) return
    axios
      .get('/campaign/info', {params: {id: id}})
      .then((response) => {
        if (response.status === 200)
          setCampaignName(response.data.name)
      })
  }

  const saveSheet = () => {
    axios
      .post('/sheets', sheet)
      .then((response) => {
        if (response.status === 200 || response.status === 201) navigate("/sheets", {state: {user: user}})
      })
  }

  const deleteSheet = () => {
    if (sheet.sheetId === -1) return
    axios
      .get('/sheets/delete', {params: {id: sheet.sheetId}})
      .then((response) => {
        if (response.status === 200) navigate("/sheets", {state: {user: user}})
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
      <div className='flex gap-2' style={{width: "100%", justifyContent: "space-evenly"}}>
        <div className='flex gap-2' style={{width: "100vw", height: "82vh", flexDirection: "column", overflowY: "scroll", padding: "1rem"}}>
          <div className='flex' style={{flexDirection: "row", width: "100%", gap: "1rem"}}>
            <div className='flex bg-secondary' style={{alignItems: "center", width: "33.1%", justifyContent: "center", borderRadius: "0.5rem"}}>
              <div className='flex' style={{flexDirection: "column", width: "95%"}}>
                <Input type="text" name="name" value={sheet.name} onChange={(e) => setSheet({...sheet, name: e.target.value})} style={{minHeight: "5rem"}} />
                <label className="text-secondary-content">Name</label>
              </div>
            </div>
            <div className='flex bg-secondary' style={{alignItems: "center", flexDirection: "column", width: "64.9%", gap: "0.5rem", padding: "1rem", borderRadius: "0.5rem"}}>
              <div className='flex' style={{alignItems: "center", width: "100%", gap: "1rem", justifyContent: "center"}}>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="text" name="class_t" value={sheet.class_t} onChange={(e) => setSheet({...sheet, class_t: e.target.value})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">Class</label>
                </div>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="text" name="background" value={sheet.background} onChange={(e) => setSheet({...sheet, background: e.target.value})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">Background</label>
                </div>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="number" name="level" value={sheet.level} onChange={(e) => setSheet({...sheet, level: Number(e.target.value)})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">Level</label>
                </div>
              </div>
              <div className='flex' style={{alignItems: "center", width: "100%", gap: "1rem", justifyContent: "center"}}>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="text" name="race" value={sheet.race} onChange={(e) => setSheet({...sheet, race: e.target.value})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">Race</label>
                </div>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="text" name="alignment" value={sheet.alignment} onChange={(e) => setSheet({...sheet, alignment: e.target.value})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">Alignment</label>
                </div>
                <div className='flex' style={{flexDirection: "column", width: "30%"}}>
                  <Input type="number" name="xp" value={sheet.xp} onChange={(e) => setSheet({...sheet, xp: Number(e.target.value)})} style={{minHeight: "3rem"}} />
                  <label className="text-secondary-content">XP</label>
                </div>
              </div>
            </div>
          </div>
          <div className='flex' style={{flexDirection: "row", width: "100%"}}>
            <div className='flex' style={{flexDirection: "column", width: "33%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
              <div className='flex' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
                <div className='flex bg-neutral' style={{flexDirection: "column", width: "30%", alignItems: "center", justifyContent: "center", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Strength</label>
                    <Input type="number" name="strength" value={sheet.strength} onChange={(e) => setSheet({...sheet, strength: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Dexterity</label>
                    <Input type="number" name="dexterity" value={sheet.dexterity} onChange={(e) => setSheet({...sheet, dexterity: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Constitution</label>
                    <Input type="number" name="constitution" value={sheet.constitution} onChange={(e) => setSheet({...sheet, constitution: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Intelligence</label>
                    <Input type="number" name="intelligence" value={sheet.intelligence} onChange={(e) => setSheet({...sheet, intelligence: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Wisdom</label>
                    <Input type="number" name="wisdom" value={sheet.wisdom} onChange={(e) => setSheet({...sheet, wisdom: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", justifyContent: "center", alignItems: "center", width: "100%", padding: "1rem", borderRadius: "0.5rem"}}>
                    <label className="text-secondary-content">Charisma</label>
                    <Input type="number" name="charisma" value={sheet.charisma} onChange={(e) => setSheet({...sheet, charisma: Number(e.target.value)})} style={{minHeight: "5.67rem", width: "100%"}} />
                  </div>
                </div>
                <div className='flex' style={{flexDirection: "column", width: "70%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
                  <div className='flex bg-secondary' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "left", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
                    <Input type="text" name="inspiration" value={sheet.inspiration} onChange={(e) => setSheet({...sheet, inspiration: e.target.value})} style={{minHeight: "3rem", width: "5rem"}} />
                    <label className="text-secondary-content">Inspiration</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "left", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
                    <Input type="number" name="proficiencyPoints" value={sheet.proficiencyPoints} onChange={(e) => setSheet({...sheet, proficiencyPoints: Number(e.target.value)})} style={{minHeight: "3rem", width: "5rem"}} />
                    <label className="text-secondary-content">Proficiency Points</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
                    <Input type="text" name="savingThrows" value={sheet.savingThrows} onChange={(e) => setSheet({...sheet, savingThrows: e.target.value})} style={{minHeight: "15rem", width: "100%"}} />
                    <label className="text-secondary-content">Saving Throws</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
                    <Textarea value={sheet.skills} onChange={(e) => setSheet({...sheet, skills: e.target.value})} style={{minHeight: "25rem", width: "100%"}}></Textarea>
                    <label className="text-secondary-content">Skills</label>
                  </div>
                </div>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "center", gap: "0.5rem", padding: "1rem", borderRadius: "0.5rem"}}>
                <Input type="text" name="perception" value={sheet.perception} onChange={(e) => setSheet({...sheet, perception: e.target.value})} style={{minHeight: "3rem", width: "4rem"}} />
                <div className='flex' style={{width: "100%", alignItems: "center", justifyContent: "center"}}>
                  <label className="text-secondary-content">Passive Wisdom (Perception)</label>
                </div>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.proficiencies} onChange={(e) => setSheet({...sheet, proficiencies: e.target.value})} style={{minHeight: "8.5rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Proficiencies</label>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.languages} onChange={(e) => setSheet({...sheet, languages: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Languages</label>
              </div>
            </div>
            <div className='flex' style={{flexDirection: "column", width: "33%", alignItems: "center", justifyContent: "center", gap: "1rem", padding: "1rem", borderRadius: "0.5rem"}}>
              <div className='flex bg-neutral' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem", borderRadius: "0.5rem", padding: "1rem"}}>
                <div className='flex' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "30%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                    <Input type="number" name="armorClass" value={sheet.armorClass} onChange={(e) => setSheet({...sheet, armorClass: Number(e.target.value)})} style={{minHeight: "6rem", width: "100%"}} />
                    <label className="text-secondary-content">Armor Class</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "30%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                    <Input type="number" name="initiative" value={sheet.initiative} onChange={(e) => setSheet({...sheet, initiative: Number(e.target.value)})} style={{minHeight: "6rem", width: "100%"}} />
                    <label className="text-secondary-content">Initiative</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "30%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                    <Input type="number" name="speed" value={sheet.speed} onChange={(e) => setSheet({...sheet, speed: Number(e.target.value)})} style={{minHeight: "6rem", width: "100%"}} />
                    <label className="text-secondary-content">Speed</label>
                  </div>
                </div>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                  <Input type="number" name="hp" value={sheet.hp} onChange={(e) => setSheet({...sheet, hp: Number(e.target.value)})} style={{minHeight: "6rem", width: "100%"}} />
                  <label className="text-secondary-content">HP</label>
                </div>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                  <Input type="number" name="temporaryHp" value={sheet.temporaryHp} onChange={(e) => setSheet({...sheet, temporaryHp: Number(e.target.value)})} style={{minHeight: "6rem", width: "100%"}} />
                  <label className="text-secondary-content">Temporary HP</label>
                </div>
                <div className='flex' style={{flexDirection: "row", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "50%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                    <Input type="text" name="hitDice" value={sheet.hitDice} onChange={(e) => setSheet({...sheet, hitDice: e.target.value})} style={{minHeight: "6rem", width: "100%", padding: "0.5rem"}} />
                    <label className="text-secondary-content">Hit Dice</label>
                  </div>
                  <div className='flex bg-secondary' style={{flexDirection: "column", width: "50%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                    <Input type="text" name="deathSaves" value={sheet.deathSaves} onChange={(e) => setSheet({...sheet, deathSaves: e.target.value})} style={{minHeight: "6rem", width: "100%", padding: "0.5rem"}} />
                    <label className="text-secondary-content">Death Saves</label>
                  </div>
                </div>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.attacks} onChange={(e) => setSheet({...sheet, attacks: e.target.value})} style={{minHeight: "9rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Attacks</label>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.spellcasting} onChange={(e) => setSheet({...sheet, spellcasting: e.target.value})} style={{minHeight: "9rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Spellcasting</label>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.equipment} onChange={(e) => setSheet({...sheet, equipment: e.target.value})} style={{minHeight: "12rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Equipment</label>
              </div>
            </div>
            <div className='flex' style={{flexDirection: "column", width: "33%", alignItems: "center", justifyContent: "center", gap: "1rem"}}>
              <div className='flex bg-neutral' style={{flexDirection: "column", width: "100%", alignItems: "center", justifyContent: "center", gap: "1rem", borderRadius: "0.5rem", padding: "1rem"}}>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                  <Textarea value={sheet.personalityTraits} onChange={(e) => setSheet({...sheet, personalityTraits: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
                  <label className="text-secondary-content">Personality Traits</label>
                </div>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                  <Textarea value={sheet.ideals} onChange={(e) => setSheet({...sheet, ideals: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
                  <label className="text-secondary-content">Ideals</label>
                </div>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1rem", borderRadius: "0.5rem"}}>
                  <Textarea value={sheet.bonds} onChange={(e) => setSheet({...sheet, bonds: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
                  <label className="text-secondary-content">Bonds</label>
                </div>
                <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                  <Textarea value={sheet.flaws} onChange={(e) => setSheet({...sheet, flaws: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
                  <label className="text-secondary-content">Flaws</label>
                </div>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.features} onChange={(e) => setSheet({...sheet, features: e.target.value})} style={{minHeight: "16.5rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Features</label>
              </div>
              <div className='flex bg-secondary' style={{flexDirection: "column", width: "100%", alignItems: "center", padding: "1.5rem", borderRadius: "0.5rem"}}>
                <Textarea value={sheet.traits} onChange={(e) => setSheet({...sheet, traits: e.target.value})} style={{minHeight: "19rem", width: "100%"}}></Textarea>
                <label className="text-secondary-content">Traits</label>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className='flex' style={{width: "100%", justifyContent: "right", alignItems: "center"}}>
        <Button className="btn btn-primary" style={{width: "10rem", height: "3rem", margin: "1rem"}} onClick={saveSheet}>Save</Button>
        <Dropdown end vertical='top'>
          <Button className="btn btn-secondary" style={{width: "15rem", height: "3rem", margin: "1rem"}}>{campaignName ? campaignName : "Selecionar campanha"}</Button>
          <Dropdown.Menu className="z-[1] menu-sm" style={{width: "10rem"}}>
            {campaignList.map((campaign) => {
              return (
                <Dropdown.Item key={campaign.campaignId} onClick={() => setCampaign(campaign.campaignId)}>{campaign.name}</Dropdown.Item>
              );
            })}
            <Dropdown.Item onClick={() => setCampaign(null)}>Nenhuma</Dropdown.Item>
          </Dropdown.Menu>
        </Dropdown>
        <Button className="btn btn-accent" style={{width: "10rem", height: "3rem", margin: "1rem"}} onClick={deleteSheet}>Delete</Button>
        <Button className="btn btn-secondary" style={{width: "10rem", height: "3rem", margin: "1rem"}} onClick={() => navigate("/sheets", {state: {user: user}})}>Cancel</Button>
      </div>
    </div>
  );          
}

export default SheetEdit;