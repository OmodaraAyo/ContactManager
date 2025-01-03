import React from "react";
import NavBar from "./NavBar";

const Header = () => {
  return (
    <div>
      <NavBar/>
      <div className="container mx-auto px-2 py-4">
        <h1 className="font-light text-3xl mb-4">Contacts</h1>
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
