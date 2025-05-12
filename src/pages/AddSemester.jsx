import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Form, Input, Button, Typography, message } from 'antd';
import { semesterApi } from '../api/api';

const { Title } = Typography;

function AddSemester() {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const teacherId = localStorage.getItem("teacherId");

  const handleSubmit = async (values) => {
    if (!values.name.trim()) {
      message.error("Semester name is required");
      return;
    }

    setLoading(true);
    try {
      await semesterApi.createSemester({
        name: values.name,
        teacherId
      });
      message.success("Semester added successfully");
      navigate('/semesters');
    } catch (err) {
      console.error("Error adding semester:", err);
      message.error("Failed to add semester");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-lg mx-auto">
      <Title level={2} className="mb-6">Add New Semester</Title>
      
      <Form
        form={form}
        layout="vertical"
        onFinish={handleSubmit}
      >
        <Form.Item
          name="name"
          label="Semester Name"
          rules={[{ required: true, message: 'Please enter semester name' }]}
        >
          <Input placeholder="Enter semester name" className="w-full" />
        </Form.Item>
        
        <div className="flex gap-4 mt-6">
          <Button 
            type="primary" 
            htmlType="submit" 
            loading={loading}
            className="bg-blue-600 hover:bg-blue-700"
          >
            Save
          </Button>
          
          <Button 
            onClick={() => navigate('/semesters')}
            className="bg-gray-200 hover:bg-gray-300"
          >
            Cancel
          </Button>
        </div>
      </Form>
    </div>
  );
}

export default AddSemester;
