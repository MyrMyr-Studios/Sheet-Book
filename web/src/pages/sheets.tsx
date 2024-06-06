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
  }

  return (
    <div>
      <a href="/sheets/edit"><h1>Sheets</h1></a>
      <div>
        {sheetList.map((sheet) => {
          return (
            <div key={sheet.id}>
              <h2>{sheet.name}</h2>
              <p>{sheet.description}</p>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default Sheets;