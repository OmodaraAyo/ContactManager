import React, { useEffect, useState } from "react";
import { useSearchContactByNameQuery } from "../service/contactApi";
import { FaSearch } from "react-icons/fa";
import { AiOutlineUser } from "react-icons/ai";
import { useNavigate } from "react-router";

const SearchBar = ({isSearchDisabled}) => {
  const navigate = useNavigate();
  const [searchedContact, setSearchedContact] = useState("");
  const [isFullLoaded, setIsFullLoaded] = useState(false);
  const [showData, setShowData] = useState(false);
  const [topMatchedContact, setTopMatchedContact] = useState(false);
  const {data: matchedContacts, isLoading,isError} = useSearchContactByNameQuery(searchedContact);

  useEffect(() => {
    if (matchedContacts) {
      setIsFullLoaded(true);
      console.log("from search", matchedContacts);
    }
  }, [matchedContacts]);

  useEffect(() => {
    if (!searchedContact.trim()) {
      setShowData(false);
    } else {
      setShowData(true);
    }
  }, [searchedContact]);

  useEffect(() => {
    if (matchedContacts) {
      matchedContacts &&
      matchedContacts[0].contacts &&
      matchedContacts[0].response
        ? setTopMatchedContact(false)
        : setTopMatchedContact(true);
    }
  }, [matchedContacts, setTopMatchedContact]);

  const handleSearchInputChange = (e) => {
    const value = e.target.value;
    if (value.length <= 40) {
      setSearchedContact(value);
    }
  };

  const navigateToContactDetailsPage = (contactId) => {
    navigate(`/contactDetails/${contactId}`)
}

  return (
    <div className="w-full flex flex-col mt-auto py-1 gap-[1.7rem]">
      <input
        type="text"
        name="searchBox"
        placeholder="Search"
        onInput={handleSearchInputChange}
        className="container px-2 bg-transparent placeholder:text-gray-600 outline-none"
        value={searchedContact}
        disabled = {isSearchDisabled}
      />
      <div
        className={`container px-2 outline-none ${
          showData
            ? "bg-slate-900 transition-transform duration-300 ease-in-out rounded-md py-3"
            : ""
        }`}
      >
        {isFullLoaded &&
        matchedContacts &&
        matchedContacts.length > 0 &&
        showData ? (
          <div>
            {topMatchedContact ? <h1 className="flex justify-start mb-3 -mt-2 text-[#00bcd4] shadow-black border-b-2 border-[#3ad0e4] font-sans px-1">Top Matched Contact{isFullLoaded && matchedContacts.length > 1? 's': ''}</h1> : ""}
            {matchedContacts.map((contact) => (
              <div key={contact.id || Math.random()} className="container" onClick={()=>navigateToContactDetailsPage(contact.id)}>
                {showData && contact.response ? (
                  <div className="text-gray-400 flex flex-col justify-center items-center py-7 gap-1 cursor-text">
                    <FaSearch className="text-lg font-bold" />
                    <h1 className="font-semibold">
                      No Results for "{searchedContact}"
                    </h1>
                    <h2 className="text-[0.950rem]">
                      Check the spelling or try a new search
                    </h2>
                  </div>
                ) : (
                  <div className="flex gap-3 font-light justify-start items-center">
                    <div className={`w-6 h-6 rounded-full border border-[#00bcd4] flex justify-center items-center`}>
                      <AiOutlineUser className={`text-lg text-[#00bcd4]`}/>
                    </div>
                    <h1 className="flex flex-col justify-self-start mb-2 text-white mt-2">
                      {contact.fullName}
                    </h1>
                  </div>
                )}
              </div>
            ))}
          </div>
        ) : (
          <h1 className="">{}</h1>
        )}
      </div>
    </div>
  );
};

export default SearchBar;
