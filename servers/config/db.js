const mongoose=require('mongoose')
 const connectdb=async()=>{
    const data= await mongoose.connect(process.env.MONGO_URI);
    console.log(data.connection.host+"connected");

 }
 module.exports=connectdb