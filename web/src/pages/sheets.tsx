import axios from 'axios'
import {useState, useEffect} from "react";

function Sheets() {
  const [sheetList, setSheetList] = useState<any[]>([]);

  useEffect(() => {
    getSheetList();
  }, []);

  const getSheetList = () => {
    axios
      .get('/sheets')
      .then((response) => {
        if (response.status === 200) setSheetList(response.data)
      })
    // setSheetList([{
    //   id: 1,
    //   name: "Sir Foo",
    //   level: 0,
    //   class_t: "",
    //   background : "",
    //   race: "",
    //   alignment: "",
    //   xp: 0,
    //   strength: 0,
    //   dexterity: 0,
    //   constitution: 0,
    //   intelligence: 0,
    //   wisdom: 0,
    //   charisma: 0,
    //   inspiration: "",
    //   proficiencyPoints: 0,
    //   armorClass: 0,
    //   initiative: 0,
    //   speed: 0,
    //   personalityTraits: "",
    //   ideals: "",
    //   bonds: "",
    //   flaws: "",
    //   features: "",
    //   traits: "",
    //   equipment: "",
    //   proficiencies: "",
    //   perception: "",
    //   languages: "",
    //   hp: 0,
    //   temporaryHp: 0,
    //   hitDice: "",
    //   deathSaves: "",
    //   attacks: "",
    //   spellcasting: "",
    //   skills: "",
    //   savingThrows: ""
    // }, {
    //   id: 2,
    //   name: "Captain Bar",
    //   level: 0,
    //   class_t: "",
    //   background : "",
    //   race: "",
    //   alignment: "",
    //   xp: 0,
    //   strength: 0,
    //   dexterity: 0,
    //   constitution: 0,
    //   intelligence: 0,
    //   wisdom: 0,
    //   charisma: 0,
    //   inspiration: "",
    //   proficiencyPoints: 0,
    //   armorClass: 0,
    //   initiative: 0,
    //   speed: 0,
    //   personalityTraits: "",
    //   ideals: "",
    //   bonds: "",
    //   flaws: "",
    //   features: "",
    //   traits: "",
    //   equipment: "",
    //   proficiencies: "",
    //   perception: "",
    //   languages: "",
    //   hp: 0,
    //   temporaryHp: 0,
    //   hitDice: "",
    //   deathSaves: "",
    //   attacks: "",
    //   spellcasting: "",
    //   skills: "",
    //   savingThrows: ""
    // }])
  }

  return (
    <div>
      <a href="/sheets/edit"><h1>Sheets</h1></a>
      <div>
        {sheetList.map((sheet) => {
          return (
            <div key={sheet.sheetId} className="bg-secondary" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem"}}>
              <label className='text-secondary-content'>{sheet.name} [ level {sheet.level} ]</label>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Sheets;