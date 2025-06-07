
const express=require('express');
const app=express();
const router=require('./routes/user')

const connectdb=require('./config/db')
const dotenv=require('dotenv')
dotenv.config({path:"./config/config.env"});
connectdb();
app.use(express.json())
const cors=require('cors');
app.use(cors());

app.use('/api/athlonet/user/auth',router);
app.use('/api/athlonet/event',require("./routes/events"));

app.use('/api/athlonet/organization',require('./routes/organizer'));


const port=process.env.PORT;

app.listen(port,'0.0.0.0',()=>{console.log("started on host in "+port)});


