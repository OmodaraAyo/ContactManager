import React from "react";
import NavBar from "../component/header/NavBar";
import { FaLongArrowAltLeft, FaUser, FaUserAlt } from "react-icons/fa";

const AddContact = () => {
  return (
    <div className="addContact-Container">
      <NavBar />
      <div className="container mx-auto px-1 py-2 flex justify-between items-center text-lg md:text-xl">
        <FaLongArrowAltLeft className="text-xl  text-slate-900" />
        <button className="bg-blue-800 px-7 py-2 rounded-full text-white">
          <h2>Save</h2>
        </button>
      </div>
      <div className="contact-image-container flex flex-col justify-self-center py-12">
        <button className=" bg-blue-100 w-[6.5rem] h-[6.5rem] rounded-full flex justify-center items-center overflow-hidden">
          <FaUser className="text-blue-300 text-8xl mt-6" />
        </button>
        <div>
          <div className="w-12 h-12 bg-white absolute top-[15.7rem] right-[20.2rem] rounded-full"></div>
          <button className="w-10 h-10 bg-blue-800 absolute top-[16rem] right-[20.4rem] rounded-full">
            <h2 className="text-3xl text-white mb-1">+</h2>
          </button>
        </div>
      </div>
      <form
        action=""
        className="form-container w-full border container mx-auto my-3"
      >
        <div className="flex justify-start items-center px-12 gap-5">
          <FaUserAlt />
          <input
            type="text"
            name="firstName"
            id=""
            placeholder="First name"
            required
            className="border-[1.5px] px-3 w-full container "
          />
        </div>
      </form>
    </div>
  );
};

export default AddContact;
