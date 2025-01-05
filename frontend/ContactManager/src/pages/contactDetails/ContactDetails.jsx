import React, { useEffect, useState } from "react";
import NavBar from "../../component/header/NavBar";
import { FaCalendarAlt, FaEllipsisV, FaLongArrowAltLeft, FaTrashAlt, FaUser } from "react-icons/fa";
import { MdChatBubble, MdEdit, MdPhone, MdVideocam } from "react-icons/md";
import { useNavigate, useParams } from "react-router";
import { useGetContactByIdQuery } from "../../service/contactApi";
import { BiEnvelope } from "react-icons/bi";

const ContactDetails = () => {
  const navigate = useNavigate();
  const {contactId} = useParams();
  const {data, isLoading, isError} = useGetContactByIdQuery(contactId);
  console.log(data)
  const [hasEmail, setHasEmail] = useState(false);
  const [hasPhoneNumber, setHasPhoneNumber] = useState(false);

  useEffect(() => {
    if(data){
      setHasEmail(!!data.email);
      setHasPhoneNumber(!!data.phoneNumber);
    }
  }, [data]);


  const navigateToHomePage = () => {
    navigate('/')
  }
  const capitalizeChatAtZero = (name) => {
    return name.charAt(0).toUpperCase() + name.slice(1).toLowerCase()
  }
  return (
    <div>
      <div className="sticky top-0 z-10">
        <NavBar />
        <div className="container px-5 sm:px-1 mx-auto py-2 flex justify-between items-center text-lg md:text-xl bg-white">
          <button onClick={() => navigateToHomePage()}><FaLongArrowAltLeft className="text-xl text-slate-900"/></button>
          <div className="btn-container flex flex-row gap-3">
            <button className="rounded-full px-2 p-2 hover:bg-gray-100"><MdEdit className="text-lg text-slate-900"/></button>
            <button className="rounded-full px-2 p-2 hover:bg-gray-100"><FaTrashAlt className="text-lg text-slate-900"/></button>
            <button className="rounded-full px-2 p-2 hover:bg-gray-100"><FaEllipsisV className="text-lg text-slate-900"/></button>
          </div>
        </div>
      </div>
      <div className="contact-image-container flex justify-end justify-self-center py-12">
        <button className="bg-blue-100 w-[12rem] h-[12rem] rounded-full flex justify-center items-center overflow-hidden">
            <FaUser className="text-blue-300 text-[11rem] mt-6" />
              </button>
            <div className="absolute mt-28 ml-">
              <div className="w-20 h-20 bg-white rounded-full absolute"></div>
            <button className="w-16 h-16 bg-blue-800 rounded-full relative mt-2 ml-2">
          <h2 className="text-3xl text-white mb-1">+</h2>
        </button>
        </div>
      </div>
      <h2 className="flex justify-center items-center -mt-8 text-xl sm:text-2xl font-sans tracking-wide font-normal mb-5">
        {isLoading && ''}
        {data && data.firstName? `${capitalizeChatAtZero(data.firstName)}`: ''}
        </h2>
      <div className="flex flex-row justify-center items-center gap-5 mb-20">
        <button className="flex flex-col justify-center items-center"><div className="rounded-full px-2 p-2 flex justify-center items-center w-10 h-10 bg-gray-200 hover:bg-gray-300"><BiEnvelope className={`text-xl sm:text-[1.3rem] ${hasEmail? 'text-black': 'text-[#ada9a9]'}`}/></div><h3 className="font-normal text-xs sm:text-sm">Email</h3></button>
        <button className="flex flex-col justify-center items-center"><div className="rounded-full px-2 p-2 flex justify-center items-center w-10 h-10 bg-gray-200 hover:bg-gray-300"><FaCalendarAlt className="text-xl sm:text-[1.2rem] text-[#ada9a9]"/></div><h3 className="font-normal text-xs sm:text-sm">Schedule</h3></button>
        <button className="flex flex-col justify-center items-center"><div className="rounded-full px-2 p-2 flex justify-center items-center w-10 h-10 bg-gray-200 hover:bg-gray-300"><MdChatBubble className={`text-xl sm:text-[1.3rem] ${hasPhoneNumber? 'text-black': 'text-[#ada9a9]'}`}/></div><h3 className="font-normal text-xs sm:text-sm">Chat</h3></button>
        <button className="flex flex-col justify-center items-center"><div className="rounded-full px-2 p-2 flex justify-center items-center w-10 h-10 bg-gray-200 hover:bg-gray-300"><MdVideocam className="text-[1.5rem] text-[#ada9a9]"/></div><h3 className="font-normal text-xs sm:text-sm">Video</h3></button>
      </div>
      <div className="contact-details-container container mx-auto px-4 py-5 bg-[#e3e8edc7] w-full rounded-md flex flex-col gap-3">
        <h1 className="text-lg tracking-wide font-medium font-sans text-slate-900">Contact details</h1>
        {hasEmail? (
         <div className="flex justify-start items-center gap-4">
          <BiEnvelope className="text-xl sm:text-[1.2rem] text-[#262525]"/>
          <button className="text-sm sm:text-base font-light text-[#2f6dfe] tracking-wide">{data.email.toLowerCase()}</button>
         </div>
        ): (<button className="text-sm sm:text-base text-[#2f6dfe]">Add email</button>)}

        {hasPhoneNumber? (
         <div className="flex justify-start items-center gap-4">
          <MdPhone className="text-xl sm:text-[1.2rem] text-[#262525]"/>
          <button className="text-sm sm:text-base font-light text-[#2f6dfe] tracking-wide">{data.phoneNumber.toLowerCase()}</button>
          <h3 className="-ml-3 -mt-2 font-medium text-lg">.</h3>
          <h4 className="-ml-3 text-[0.796rem] font-medium">Mobile</h4>
         </div>
        ): (<button className="text-sm sm:text-base text-[#2f6dfe]">Add phone number</button>)}
      </div>
    </div>
  );
};

export default ContactDetails;
