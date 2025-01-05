import React, { useRef, useState } from "react";
import NavBar from "../component/header/NavBar";
import { FaLongArrowAltLeft, FaUser, FaUserAlt } from "react-icons/fa";
import { BiEnvelope } from "react-icons/bi";
import { MdPhone } from "react-icons/md";
import { useAddContactMutation } from "../service/contactApi";
import { toast, Bounce } from "react-toastify";
import { useNavigate } from "react-router";

const AddContact = () => {
  const [firstName, setFirstName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [isFocused, setIsFocused] = useState(false);
  const navigate = useNavigate();
  const [addContact, { isLoading }] = useAddContactMutation();

  const handleSubmit = async (e) => {
    e.preventDefault();

    const contactData = {
      firstName,
      surname,
      email,
      phoneNumber,
    };

    try {
      const response =  await addContact(contactData).unwrap();
      const contactId = response.id;
      navigate(`/contactDetails/${contactId}`);
      toast.success("Contact saved succesfully", {
        position: "bottom-right",
        autoClose: 5000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: false,
        progress: undefined,
        theme: "colored",
        transition: Bounce,
      });

      setFirstName("");
      setSurname("");
      setEmail("");
      setPhoneNumber("");
    } catch (error) {
      // console.log('from add contact error:', error);
      toast.error(`Failed to add contact: ${error.data.message}`, {
        position: "bottom-right",
        autoClose: 5000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: false,
        progress: undefined,
        theme: "dark",
        transition: Bounce,
      });
    }
  };

  return (
    <div className="addContact-Container">
      <div className="sticky top-0 z-10">
        <NavBar />
        <div className="container px-5 sm:px-1 mx-auto py-2 flex justify-between items-center text-lg md:text-xl bg-white">
          <FaLongArrowAltLeft className="text-xl text-slate-900" />
          <button
            onClick={handleSubmit}
            form="a-form"
            type="button"
            className="save-button bg-blue-800 px-7 py-2 rounded-full text-white"
            disabled={isLoading}
          >
            <h2>Save</h2>
          </button>
        </div>
      </div>
      <div className="contact-image-container flex justify-end justify-self-center py-12 ">
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
      <form
        id="a-form"
        className="form-container w-full  container mx-auto my-3"
      >
        <div className="flex flex-col gap-5 justify-self-center w-full max-w-xl">
          <div className="flex items-center px-12 gap-5">
            <FaUserAlt className="text-2xl text-slate-900" />
            <input
              type="text"
              name="firstName"
              placeholder="First name"
              value={firstName}
              onChange={(e) => setFirstName(e.target.value)}
              required
              className="border border-black px-3 py-2 w-full container outline-blue-900"
            />
          </div>
          <div className="flex items-center px-12 ml-[1.4rem]">
            <div className="flex items-center ml-[1.9rem] absolute mb-[2.7rem] z-30 bg-white">
              <h2
                className={`italic font-light font-serif text-sm ${
                  isFocused ? "text-blue-900" : ""
                }`}
              >
                Optional
              </h2>
            </div>
            <input
              type="text"
              name="surname"
              placeholder="Surname"
              value={surname}
              onChange={(e) => setSurname(e.target.value)}
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              className={`border border-black px-3 py-2 w-full container ${
                isFocused ? "outline-blue-900" : ""
              } ml-[1.2rem]`}
            />
          </div>
          <div className="flex items-center px-12 gap-5">
            <BiEnvelope className="text-2xl text-slate-900" />
            <input
              type="text"
              name="email"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="border border-black px-3 py-2 w-full container outline-blue-900"
            />
          </div>
          <div className="flex items-center px-12 gap-5">
            <MdPhone className="text-2xl text-slate-900" />
            <input
              type="text"
              name="phone number"
              placeholder="Phone"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              required
              className="border border-black px-3 py-2 w-full container outline-blue-900"
            />
          </div>
        </div>
      </form>
    </div>
  );
};

export default AddContact;
