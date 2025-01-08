import React from "react";
import { useNavigate } from "react-router";
import { useDeleteContactByIdMutation } from "../service/contactApi";
import { FaTrashAlt } from "react-icons/fa";
import { Bounce, toast } from "react-toastify";

const DeleteBtn = ({ contactId }) => {
  const navigate = useNavigate();
  const [
    deleteContactById,
    {isLoading: isLoadingDeleteContact,
    isError: isErrorDeleteContact,}
   ] = useDeleteContactByIdMutation();

  const handleDeteContact = async () => {

    try {
      await deleteContactById(contactId).unwrap();
      toast.error("Contact deleted", {
        position: "bottom-right",
        autoClose: 1000,
        hideProgressBar: true,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: false,
        progress: undefined,
        theme: "colored",
        transition: Bounce,
      });
      navigate(`/`);
    } catch (error) {
      toast.error(`Failed to delete contact: ${error.data.message}`, {
        position: "bottom-right",
        autoClose: 1000,
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
    <button
      className="rounded-full px-2 p-2 hover:bg-gray-100"
      onClick={handleDeteContact}
      disabled={isLoadingDeleteContact}
    >
      <FaTrashAlt className="text-lg text-slate-900" />
    </button>
  );
};

export default DeleteBtn;
