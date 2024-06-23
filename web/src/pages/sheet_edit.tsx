import axios from 'axios'
import {useState, useEffect} from "react";
import { Button, Input, Textarea } from 'react-daisyui';

function SheetEdit() {
  const [sheet, setSheet] = useState({
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

  const getSheet = () => {
    axios
      .get('/sheets')
      .then((response) => {
        if (response.status === 200) setSheet(response.data)
      })
  }

  return (
    <div>
      <div className='flex gap-2' style={{width: "100%", justifyContent: "space-evenly"}}>
        <div className='flex gap-2' style={{width: "50vw", height: "100vh", flexDirection: "column", overflowY: "scroll", padding: "1rem"}}>
          <label>Name</label>
          <Input type="text" name="name" value={sheet.name} onChange={(e) => setSheet({...sheet, name: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Level</label>
          <Input type="number" name="level" value={sheet.level} onChange={(e) => setSheet({...sheet, level: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Class</label>
          <Input type="text" name="class_t" value={sheet.class_t} onChange={(e) => setSheet({...sheet, class_t: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Background</label>
          <Input type="text" name="background" value={sheet.background} onChange={(e) => setSheet({...sheet, background: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Race</label>
          <Input type="text" name="race" value={sheet.race} onChange={(e) => setSheet({...sheet, race: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Alignment</label>
          <Input type="text" name="alignment" value={sheet.alignment} onChange={(e) => setSheet({...sheet, alignment: e.target.value})} style={{minHeight: "3rem"}} />
          <label>XP</label>
          <Input type="number" name="xp" value={sheet.xp} onChange={(e) => setSheet({...sheet, xp: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Strength</label>
          <Input type="number" name="strength" value={sheet.strength} onChange={(e) => setSheet({...sheet, strength: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Dexterity</label>
          <Input type="number" name="dexterity" value={sheet.dexterity} onChange={(e) => setSheet({...sheet, dexterity: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Constitution</label>
          <Input type="number" name="constitution" value={sheet.constitution} onChange={(e) => setSheet({...sheet, constitution: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Intelligence</label>
          <Input type="number" name="intelligence" value={sheet.intelligence} onChange={(e) => setSheet({...sheet, intelligence: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Wisdom</label>
          <Input type="number" name="wisdom" value={sheet.wisdom} onChange={(e) => setSheet({...sheet, wisdom: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Charisma</label>
          <Input type="number" name="charisma" value={sheet.charisma} onChange={(e) => setSheet({...sheet, charisma: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Inspiration</label>
          <Input type="text" name="inspiration" value={sheet.inspiration} onChange={(e) => setSheet({...sheet, inspiration: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Proficiency Points</label>
          <Input type="number" name="proficiencyPoints" value={sheet.proficiencyPoints} onChange={(e) => setSheet({...sheet, proficiencyPoints: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Armor Class</label>
          <Input type="number" name="armorClass" value={sheet.armorClass} onChange={(e) => setSheet({...sheet, armorClass: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Initiative</label>
          <Input type="number" name="initiative" value={sheet.initiative} onChange={(e) => setSheet({...sheet, initiative: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Speed</label>
          <Input type="number" name="speed" value={sheet.speed} onChange={(e) => setSheet({...sheet, speed: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Personality Traits</label>
          <Textarea value={sheet.personalityTraits} onChange={(e) => setSheet({...sheet, personalityTraits: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Ideals</label>
          <Textarea value={sheet.ideals} onChange={(e) => setSheet({...sheet, ideals: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Bonds</label>
          <Textarea value={sheet.bonds} onChange={(e) => setSheet({...sheet, bonds: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Flaws</label>
          <Textarea value={sheet.flaws} onChange={(e) => setSheet({...sheet, flaws: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Features</label>
          <Textarea value={sheet.features} onChange={(e) => setSheet({...sheet, features: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Traits</label>
          <Textarea value={sheet.traits} onChange={(e) => setSheet({...sheet, traits: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Equipment</label>
          <Textarea value={sheet.equipment} onChange={(e) => setSheet({...sheet, equipment: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Proficiencies</label>
          <Textarea value={sheet.proficiencies} onChange={(e) => setSheet({...sheet, proficiencies: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Perception</label>
          <Input type="text" name="perception" value={sheet.perception} onChange={(e) => setSheet({...sheet, perception: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Languages</label>
          <Textarea value={sheet.languages} onChange={(e) => setSheet({...sheet, languages: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>HP</label>
          <Input type="number" name="hp" value={sheet.hp} onChange={(e) => setSheet({...sheet, hp: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Temporary HP</label>
          <Input type="number" name="temporaryHp" value={sheet.temporaryHp} onChange={(e) => setSheet({...sheet, temporaryHp: Number(e.target.value)})} style={{minHeight: "3rem"}} />
          <label>Hit Dice</label>
          <Input type="text" name="hitDice" value={sheet.hitDice} onChange={(e) => setSheet({...sheet, hitDice: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Death Saves</label>
          <Input type="text" name="deathSaves" value={sheet.deathSaves} onChange={(e) => setSheet({...sheet, deathSaves: e.target.value})} style={{minHeight: "3rem"}} />
          <label>Attacks</label>
          <Textarea value={sheet.attacks} onChange={(e) => setSheet({...sheet, attacks: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Spellcasting</label>
          <Textarea value={sheet.spellcasting} onChange={(e) => setSheet({...sheet, spellcasting: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Skills</label>
          <Textarea value={sheet.skills} onChange={(e) => setSheet({...sheet, skills: e.target.value})} style={{minHeight: "6rem", width: "100%"}}></Textarea>
          <label>Saving Throws</label>
          <Input type="text" name="savingThrows" value={sheet.savingThrows} onChange={(e) => setSheet({...sheet, savingThrows: e.target.value})} style={{minHeight: "3rem"}} />
        </div>
        <div>
          <Button className="btn btn-accent" style={{width: "10rem", height: "3rem", margin: "1rem"}}>Save</Button>
          <Button className="btn btn-accent" style={{width: "10rem", height: "3rem", margin: "1rem"}}>Add to Campaign</Button>
          <Button className="btn btn-secondary" style={{width: "10rem", height: "3rem", margin: "1rem"}}>Delete</Button>
          <Button className="btn btn-secondary" style={{width: "10rem", height: "3rem", margin: "1rem"}}>Cancel</Button>
          <pre style={{width: "50vw", height: "90vh", overflowY: "scroll"}}>{JSON.stringify(sheet, null, 2)}</pre>
        </div>
      </div>
    </div>
  );          
}

export default SheetEdit;