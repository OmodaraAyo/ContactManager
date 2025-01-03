import React from "react";
import Header from "../component/header/Header";
import AddContact from "../reusable/AddContactBtn";

const Layout = () => {
  return (
    <div>
      <Header />
      <div className="container mx-auto flex justify-end ">
        <AddContact/>
      </div>
    </div>
  );
};

export default Layout;
