import axios from 'axios'
import {useState, useEffect} from "react";
import { Link } from 'react-router-dom';

function Campaigns() {
  const [campaignList, setCampaignList] = useState<any[]>([]);

  useEffect(() => {
    getCampaignList();
  }, []);

  const getCampaignList = () => {
    axios
      .get('/campaigns')
      .then((response) => {
        if (response.status === 200) setCampaignList(response.data)
      })
  }

  return (
    <div>
      <div>
        {campaignList.map((campaign) => {
          return (
            <Link to="/sheets/edit" state={campaign} key={campaign.campaignId}>
              <div className="bg-secondary" style={{borderRadius: "1rem", padding: "1rem", margin: "1rem"}}>
                <span className='text-secondary-content'>{campaign.name}</span>
              </div>
            </Link>
          );
        })}
      </div>
    </div>
  );
}

export default Campaigns;