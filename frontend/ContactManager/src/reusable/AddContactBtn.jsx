import React, { useState } from "react";
import { AiOutlineUser } from "react-icons/ai";
import { useNavigate } from "react-router";

const AddContact = () => {
  const [showCreateAContact, setShowCreateAContact] = useState(false);
  const navigate = useNavigate();

  const navigateToAddContactPage = () =>{
      navigate('/addContact')
  }
  return (
    <div className={`absolute w-full h-full max-w-72 max-h-28 flex rounded-xl z-10 bottom-0 mb-4 ${showCreateAContact? 'justify-between items-start': 'justify-end items-start'}`}>
      {showCreateAContact && (
        <div className="flex w-full h-14 max-w-[14.4rem] bg-slate-100 opacity-85 rounded-2xl mt-1">
          <button onClick={() => navigateToAddContactPage()} className="flex justify-start items-center gap-2 w-full px-5">
            <AiOutlineUser className="text-2xl" />
            <p className="text-lg">Create a contact</p>
          </button>
        </div>
      )}
      <button onClick={()=> setShowCreateAContact(prev => !prev)} className={`w-14 h-14 flex justify-center items-center bg-gray-950 opacity-85 text-white text-4xl pb-2 rounded-xl shadow-md shadow-gray-700 mt-14`}>
        <p>+</p>
      </button>
    </div>
  );
};

export default AddContact;
