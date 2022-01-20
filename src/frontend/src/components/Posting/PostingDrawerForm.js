import {Drawer, Input, Col, Select, Form, Row, Button} from 'antd';
import { addNewPosting, getAllStudents } from '../../client';
import {successNotification, errorNotification} from "../../Notification";
import {useState} from 'react';


function PostingDrawerForm({showDrawer, setShowDrawer, fetchPostings}) {
    const onCLose = () => setShowDrawer(false);
    const [submitting, setSubmitting] = useState(false);


    const onFinish = posting => {
        console.log("Dentro do form")
        console.log(JSON.stringify(posting, null, 2))
         

        addNewPosting(posting)
            .then(() => {
                console.log("Posting add.")
                onCLose();
                fetchPostings();
                successNotification(
                    "System notification",
                    "Message successfully added"
                    )
            }).catch(err => {
                console.log(err)
            }).finally(() => {
                setSubmitting(false);
        })

    };

    const onFinishFailed = errorInfo => {
        alert(JSON.stringify(errorInfo, null, 2));
    };


    return <Drawer
        title="Create new post"
        width={520}
        onClose={onCLose}
        visible={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onCLose} style={{marginRight: 8}}>
                    Cancel
                </Button>
            </div>
        }
    >
        <Form layout="vertical"
              onFinishFailed={onFinishFailed}
              onFinish={onFinish}
              hideRequiredMark>
            <Form.Item
                name="description"
                label="Message"
                rules={[{required: true, message: 'Please write something...'}]}
            >
                <Input.TextArea showCount maxLength={100} autoSize={{ minRows: 6, maxRows: 10 }} />
            </Form.Item>
            
            <Row>
                <Col span={12}>
                    <Form.Item >
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Col>
            </Row>
        </Form>
    </Drawer>
}

export default PostingDrawerForm;