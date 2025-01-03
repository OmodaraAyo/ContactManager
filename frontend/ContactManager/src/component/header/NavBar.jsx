import React, { useState } from "react";
import { AiOutlineUser } from "react-icons/ai";
import { BsGear } from "react-icons/bs";
import { FaBars, FaSearch } from "react-icons/fa";
import { FaUser } from "react-icons/fa6";
import SideBar from "./SideBar";

const NavBar = () => {
  const [showSideBar, setShowSideBar] = useState(false);

  return (
    <div className="main-container w-full flex bg-gray-950 bg-opacity-10 sticky top-0">
      {showSideBar && <SideBar setShowSideBar={setShowSideBar} />}
      <div className="container mx-auto py-5 flex justify-between items-center">
        <div className="logo-And-SideBar-container flex flex-row justify-start items-center gap-1">
          <button
            onClick={() => {
              setShowSideBar(true);
            }}
            className="hover:bg-gray-400 hover:bg-opacity-45 rounded-full w-9 h-9 flex justify-center items-center"
          >
            {showSideBar ? (
              " "
            ) : (
              <FaBars className="text-2xl text-slate-900 outline-none" />
            )}
          </button>
          <div className="logo-container flex flex-row justify-center items-center gap-2">
            <FaUser className="text-2xl text-blue-800" />
            <h1 className="font-semibold font-sans text-2xl text-slate-900">
              Contacts
            </h1>
          </div>
        </div>
        <button className="search-bar flex flex-row justify-center items-center bg-slate-100 opacity-85 rounded-md border-slate-100 w-full h-9 max-w-80 px-2 outline-none">
          <FaSearch className="text-slate-900" />
          <input
            type="text"
            name="searchBox"
            placeholder="Search"
            className="container px-2 bg-transparent placeholder:text-gray-600 outline-none"
          />
        </button>
        <div className="user-buttons flex justify-start items-center gap-1">
          <button className="flex justify-center items-center w-7 h-7 rounded-full outline-none">
            <div className="w-5 h-5 rounded-full border-2 border-[#f39c12] bg-transparent flex justify-center items-center">
              <p className="font-serif text-[#f39c12]">?</p>
            </div>
          </button>
          <button className="flex justify-center items-center w-7 h-7 hover:bg-gray-400 hover:bg-opacity-45 rounded-full outline-none">
            <BsGear className="text-xl text-slate-900" />
          </button>
          <button className="flex justify-center items-center w-6 h-6 hover:bg-gray-400 hover:bg-opacity-45 rounded-full outline-none">
            <div className="w-6 h-6 rounded-full border border-black flex justify-center items-center">
              <AiOutlineUser className="text-lg text-slate-900" />
            </div>
          </button>
        </div>
      </div>
    </div>
  );
};

export default NavBar;
