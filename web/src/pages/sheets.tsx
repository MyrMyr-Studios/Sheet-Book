import axios from 'axios'
import {useState, useEffect} from "react";
import { Link } from 'react-router-dom';

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
            <Link to="/sheets/edit" state={sheet} key={sheet.sheetId}>
              <div className="bg-secondary" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem"}}>
                <span className='text-secondary-content'>{sheet.name} [ level {sheet.level} ]</span>
              </div>
            </Link>
          );
        })}
      </div>
    </div>
  );
}

export default Sheets;