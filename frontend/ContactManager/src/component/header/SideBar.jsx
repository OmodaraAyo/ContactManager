import React from "react";
import { FaLongArrowAltLeft, FaTrashAlt, FaUser } from "react-icons/fa";

const SideBar = ({ setShowSideBar }) => {
  const handleCloseSideBar = () => {
    setShowSideBar(false);
  };
  return (
    <div
      className={`absolute w-full h-screen max-w-64 bg-slate-900 bg-opacity-100 transition-transform duration-[5000ms] ease-in-out z-10 text-white ${
        setShowSideBar
          ? "transform translate-x-0"
          : "transform -translate-x-full"
      }`}
    >
      <div className="bg-slate-800 w-full h-screen bg-opacity-70 absolute -z-20"></div>
      <div className="flex justify-self-end px-5 py-7">
        <button onClick={handleCloseSideBar} className="border px-2 py-2 rounded-lg shadow-md shadow-slate-900">
          <FaLongArrowAltLeft className="text-white text-2xl" />
        </button>
      </div>
      <div className="flex flex-col px-2 gap-5">
        <button className="contact-button-container text-white flex justify-self-start items-center gap-3 hover:bg-slate-100 hover:bg-opacity-5 px-3 py-2 rounded-full">
          <FaUser className="text-blue-800" />
          <h2>Contacts</h2>
        </button>
        <label htmlFor="fix-And-Manage" className="flex px-3 text-lg">
          Fix and manage
        </label>
        <button className="Trash-button flex flex-row justify-start items-center gap-3 hover:bg-slate-100 hover:bg-opacity-5 px-3 py-2 rounded-full">
          <FaTrashAlt />
          <h2>Bin</h2>
        </button>
      </div>
    </div>
  );
};

export default SideBar;
