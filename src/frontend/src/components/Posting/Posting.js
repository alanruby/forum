import React from 'react';
import { useState, useEffect } from 'react';
import { getPostings, getPostingsByUser, addSupport } from '../../client';
import '../../App.css';
import './Posting.css';
import { Card , Col, Row, Button, Badge} from 'antd';
import {successNotification, errorNotification} from "../../Notification";

import { Layout, Menu, Breadcrumb } from 'antd';
import {
    DesktopOutlined,
    PieChartOutlined,
    FileOutlined,
    TeamOutlined,
    UserOutlined,
    PlusOutlined
} from '@ant-design/icons';

import PostingDrawerForm from "./PostingDrawerForm"

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

export default function Posting() {
 const [postings, setPostings] = useState([]);
 const [collapsed, setCollapsed] = useState(false);
 const [fetching, setFetching] = useState(true);
 const [showDrawer, setShowDrawer] = useState(false);


 const fetchPostings = () =>
 	getPostings()
 		.then(res => res.json())
 		.then(data => {
 			console.log(data);
 			setPostings(data);
 		})

const fetchPostingsByUser = () =>
    getPostingsByUser()
        .then(res => res.json())
        .then(data => {
            console.log(data);
            setPostings(data);
        })


 const supportPosting = (postingId, callback) => {
    addSupport(postingId).then( (res) => {
        console.log("RES")
        console.log(res)
        successNotification( "System notification", "Support saved. Thanks!")
        callback();
    }).catch(
    err =>  {
        console.log(err.response)
        err.response.json().then(
            res => {
            console.log(res);
            errorNotification(res.message)  
        });
    });
}

 useEffect(() =>{
 	console.log("component is mounted");
 	fetchPostings();
 },[]);

  return <>
  
    <PostingDrawerForm
    	showDrawer={showDrawer}
    	setShowDrawer={setShowDrawer}
    	fetchPostings={fetchPostings}
    />
  
  	<Layout style={{ minHeight: '100vh' }}>
        
        <Layout className="site-layout">
            <Header className="site-layout-background" style={{ padding: 0 }} />
            <Content style={{ margin: '0 16px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>User</Breadcrumb.Item>
                    <Breadcrumb.Item>{sessionStorage.getItem('username')}</Breadcrumb.Item>
                </Breadcrumb>
                 <Button
                      onClick={() => setFetching(fetchPostingsByUser)}
                      type="primary" shape="" ghost>
                      Supported posts
                  </Button>  <Button
                      onClick={() => setFetching(fetchPostings)}
                      type="primary" shape="" ghost>
                      Postings
                  </Button>
                <div className="site-layout-background" style={{ padding: 24, minHeight: 360 }}>
                	<Button type="primary" onClick={() => setShowDrawer(!showDrawer)} shape="round" icon={<PlusOutlined />} size="small">Add New Post</Button>
                    <Row gutter={10}>
                        {postings.map((posting,index) => {
                        	return (<Col span={5}>
			                    <Card title={posting.createdAt} style={{ width: 300, marginTop: 16 }}>
							      <p>{posting.description}</p>
                                  <p style={{ textAlign: 'right' }}>
                                    <Button type="default" onClick={() => supportPosting(posting.id,fetchPostings)}  icon={<PlusOutlined />} size="small">Support</Button>
                                    {<Badge  count={posting.upvotes}  className='site-badge-count-4'/>}
                                  </p>
							     
						    	</Card>
						    </Col>

                        )})}
					</Row>
				   
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>By Alan ©2022</Footer>
        </Layout>
    </Layout>
    </>
}
