import React, { useEffect, useState } from "react";
import { AiOutlineUser } from "react-icons/ai";
import { BsGear } from "react-icons/bs";
import { FaBars, FaLongArrowAltLeft, FaSearch } from "react-icons/fa";
import { FaUser } from "react-icons/fa6";
import SideBar from "./SideBar";
import SearchBar from "../../reusable/SearchBar";

const NavBar = ({isSearchDisabled}) => {
  const [showSideBar, setShowSideBar] = useState(false);
  const [scrolling, setScrolling] = useState(false);
  const [showSearchButton, setShowSearchButton] = useState(false);

  const handleShowSearchButtonAndSideBar = () => {
    setShowSearchButton(true);
    setShowSideBar(false);
  }

  useEffect(() => {
    const handleScroll = () => {
      window.scrollY > 50 ? setScrolling(true) : setScrolling(false);
    };
    window.addEventListener("scroll", handleScroll);

    return () => window.removeEventListener("scroll", handleScroll);
  }, []);

  useEffect(()=> {
    const handleResize = () => {
      window.innerWidth > 640? setShowSearchButton(false): ''
    }

    window.addEventListener('resize', handleResize);

    handleResize();

    return () => window.removeEventListener('resize', handleResize)
  }, []);
  
  return (
    <div
      className={`main-container w-full flex sticky top-0 z-20 ${
        scrolling ? "bg-gray-800" : `bg-gray-950 bg-opacity-10 `
      }`}
    >
      {showSideBar && <SideBar setShowSideBar={setShowSideBar} setShowSearchButton={setShowSearchButton} />}
      <div className="container px-4 sm:px-0 sm:mx-auto py-5 flex justify-between items-center">
        <div className="logo-And-SideBar-container flex flex-row justify-start items-center gap-1">
          <button
            onClick={() => {
              setShowSideBar(true);
            }}
            className="hover:bg-gray-400 hover:bg-opacity-45 rounded-full w-9 h-9 flex justify-center items-center outline-none"
          >
            {showSideBar ? (
              " "
            ) : (
              <FaBars
                className={`text-2xl outline-none ${
                  scrolling ? "text-white" : "text-slate-900"
                }`}
              />
            )}
          </button>
          {showSearchButton ? (
            <FaUser className="text-2xl text-blue-800" />
          ) : (
            <div className="logo-container flex flex-row justify-center items-center gap-2">
              <FaUser className="text-2xl text-blue-800" />
              <h1
                className={`font-semibold font-sans text-2xl ${
                  scrolling ? "text-white" : "text-slate-900"
                }`}
              >
                Contacts
              </h1>
            </div>
          )}
        </div>
        <button className="search-bar hidden sm:flex sm:flex-row justify-center items-center bg-slate-100 opacity-85 rounded-md border-slate-100 w-full h-9 max-w-[21rem] lg:max-w-[30rem] xl:max-w-[45rem] px-2 outline-none" 
        disabled={isSearchDisabled} >
          <FaSearch className="text-slate-900" />
          <SearchBar isSearchDisabled={isSearchDisabled}/>
        </button>
        <div className="user-buttons flex justify-start items-center gap-1">
          <button
            onClick={() => handleShowSearchButtonAndSideBar()}
            className=" sm:hidden mt-1"
          >
            {showSearchButton ? (
              ""
            ) : (
              <FaSearch className="text-[1.37rem] text-slate-900" />
            )}
          </button>
          {showSearchButton ? (
            <form className="search-bar flex flex-row gap-5 justify-start items-center bg-slate-100 opacity-85 rounded-md border-slate-100 h-9 px-2 outline-none transform transition-transform duration-300 ease-in-out cursor-pointer">
              <button onClick={() => setShowSearchButton(false)} className="w-8 h-8 rounded-full hover:bg-gray-200">
                <FaLongArrowAltLeft className="text-2xl text-slate-900"/>
              </button>
              <SearchBar isSearchDisabled={isSearchDisabled}/>
            </form>
          ) : (
            ""
          )}
          <button className="flex justify-center items-center w-7 h-7 rounded-full outline-none">
            <div className="w-6 h-6 rounded-full border-2 border-[#f39c12] bg-transparent flex justify-center items-center">
              <p className="font-serif text-[#f39c12]">?</p>
            </div>
          </button>
          <button className="flex justify-center items-center w-7 h-7 hover:bg-gray-400 hover:bg-opacity-45 rounded-full outline-none">
            <BsGear
              className={`text-2xl ${
                scrolling ? "text-green-200" : "text-slate-900"
              }`}
            />
          </button>
          <button className="flex justify-center items-center w-8 h-8 hover:bg-gray-400 hover:bg-opacity-45 rounded-full outline-none">
            <div
              className={`w-6 h-6 rounded-full border ${
                scrolling ? "border-white" : "border-black"
              } flex justify-center items-center`}
            >
              <AiOutlineUser
                className={`text-lg ${
                  scrolling ? "text-white" : "text-slate-900"
                }`}
              />
            </div>
          </button>
        </div>
      </div>
    </div>
  );
};

export default NavBar;
