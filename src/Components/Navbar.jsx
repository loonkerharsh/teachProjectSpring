import React, { useState } from "react";
import { MenuOutlined } from "@ant-design/icons";
import { Drawer, Button, Menu } from "antd";
import { Link } from "react-router-dom";

const Navbar = () => {
  const [visible, setVisible] = useState(false);

  const menuItems = [
    { label: <Link to="/">Home</Link>, key: "home" },
    { label: <Link to="/about">About</Link>, key: "about" },
    { label: <Link to="/services">Services</Link>, key: "services" },
    { label: <Link to="/contact">Contact</Link>, key: "contact" },
  ];

  return (
    <nav className="bg-white shadow-md px-6 py-3 flex items-center justify-between">
      {/* Logo */}
      <div className="text-2xl font-bold text-blue-600">
        <Link to="/">MyApp</Link>
      </div>

      {/* Desktop Menu */}
      <div className="hidden md:flex gap-6">
        <Menu mode="horizontal" items={menuItems} />
      </div>

      {/* Mobile Menu Icon */}
      <div className="md:hidden">
        <Button
          type="text"
          icon={<MenuOutlined />}
          onClick={() => setVisible(true)}
        />
        <Drawer
          title="Menu"
          placement="right"
          onClose={() => setVisible(false)}
          open={visible}
        >
          <Menu mode="vertical" items={menuItems} />
        </Drawer>
      </div>
    </nav>
  );
};

export default Navbar;
