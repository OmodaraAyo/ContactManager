import React, { useEffect, useState } from "react";
import NavBar from "./NavBar";
import { useGetAllContactQuery } from "../../service/contactApi";
import { useNavigate } from "react-router";

const Header = () => {
  const {data, isSuccess, isLoading, isError} = useGetAllContactQuery();
  const [isArray, setIsArray] = useState(false);
  const [showPhoneNumber, setShowPhoneNumber] = useState(false);
  const navigate = useNavigate();
  
  const navigateToContactDetailsPage = (contactId) => {
      navigate(`/contactDetails/${contactId}`)
  }

  useEffect(()=> {
    if(data){
      setIsArray(!!data);
    }
  }, [data]);

  useEffect(()=>{
    const handleResize = () => {
      window.innerWidth > 768? setShowPhoneNumber(true): setShowPhoneNumber(false)
    }

    window.addEventListener('resize', handleResize);

    handleResize();

    return () => window.removeEventListener('resize', handleResize);
  }, [])
  
  return (
    <div>
      <NavBar/>
      <div className="container mx-auto px-5 sm:px-1 py-4">
        <h1 className="flex flex-row gap-2 justify-start items-center font-light text-3xl mb-4">Contacts <p className="text-lg sm:text-xl mt-2">{`${isArray? data.contacts.length == 0? '': (`(${data.contacts.length})`) : ''}`}</p></h1>
        <div className="grid grid-cols-3 justify-between px-1 text-black text-opacity-75 border-b-2 border-gray-950 border-opacity-10 pb-2">
          <h2>Name</h2>
          <h2>Email</h2>
          {showPhoneNumber? (<h2 className="xl:ml-10">Phone number</h2>): ''}
        </div>
        <div className="contact-list-container py-5">
          {`${isLoading? 'Loading...': ''}`}
          {`${isError? '': ''}`}
          {isSuccess && data.contacts.map((contact) => (
            <div key={contact.id} onClick={() => navigateToContactDetailsPage(contact.id)} className="contact-container container mx-auto grid grid-cols-3 xl:flex xl:flex-row justify-between mb-5 cursor-pointer">
                <h2 className="w-full max-w-52">{contact.fullName}</h2>
                <h2 className="flex justify-start w-full max-w-60">{contact.email}</h2>
                {showPhoneNumber? (
                     <h2 className="w-full max-w-96">{contact.phoneNumber}</h2>
                ): ''}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default Header;
