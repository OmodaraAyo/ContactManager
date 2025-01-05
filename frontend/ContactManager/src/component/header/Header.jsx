import React, { useEffect, useState } from "react";
import NavBar from "./NavBar";
import { useGetAllContactQuery } from "../../service/contactApi";

const Header = () => {
  const {data, isLoading, isError} = useGetAllContactQuery();
  const [isArray, setIsArray] = useState(false);
  console.log(data);

  useEffect(()=> {
    if(data){
      setIsArray(!!data);
    }
  }, [data]);
  
  return (
    <div>
      <NavBar/>
      <div className="container mx-auto px-2 py-4">
        <h1 className="flex flex-row gap-2 justify-start items-center font-light text-3xl mb-4">Contacts <h2 className="text-lg sm:text-xl mt-2">{`(${isArray? data.contacts.length: ''})`}</h2></h1>
        <div className="flex flex-row justify-between px-1 text-black text-opacity-75 border-b-2 border-gray-950 border-opacity-10 pb-2">
          <h2>Name</h2>
          <h2>Email</h2>
          <h2>Phone number</h2>
        </div>
      </div>
    </div>
  );
};

export default Header;
