package com.texon.engineeringsmartbook.data.model.booksModel

data class BookDashboardData(
    var `data`: Data,
    var errors: String,
    var message: String, // OK
    var success: Boolean // true
) {
    data class Data(
        var avatar: String, // https://engineeringsmartbook.com/storage/books/November2021/F5abVIPh5xAjfVjv3Lvq.png
        var chapterDetails: ArrayList<ChapterDetail>,
        var created_at: String, // 2021-11-11T05:11:23.000000Z
        var deleted_at: Any, // null
        var demo_content: String, // []
        var description: String, // <h2 class="MsoNormal" style="text-align: center;"><span style="background-color: #ffffff; color: #ff0000;"><strong><span style="font-size: 20pt; line-height: 107%; font-family: SutonnyMJ; background-color: #ffffff;">বইটির বৈশিষ্ট্যঃ&nbsp;&nbsp;</span></strong></span></h2><p><!-- [if !supportLists]--></p><h5><span style="font-size: 16.0pt; line-height: 107%; font-family: Wingdings; mso-fareast-font-family: Wingdings; mso-bidi-font-family: Wingdings; mso-bidi-font-weight: bold;"><span style="mso-list: Ignore;">প্রত্যেকটি টপিককে বেসিক থেকে সিক্যুয়েন্স অনুযায়ী আলোচনা করে বিভিন্ন দেশী বিদেশী স্বনামধন্য লেখকের বই থেকে পর্যাপ্ত গানিতিক সমস্যা এবং বিগত বিভিন্ন চাকুরি পরীক্ষার প্রশ্ন বিস্তারিত ব্যাখ্যাসহ উপস্থাপন করা হয়েছে।</span></span></h5><h5>&nbsp;</h5><h5><span style="font-family: Wingdings;"><span style="font-size: 21.3333px;">প্রত্যেকটি টপিকে বেসিক সহকারে ভিডিও ক্লাসের সার্পোট যেখানে বই থেকে সরাসরি নির্ধারীত ক্লাসের ভিডিওতে যাবার ব্যবস্থা যাহা যেকোনো সদ্য পাশকৃত শিক্ষার্থীর টিউটরের বিকল্প </span></span><span style="font-family: Wingdings; font-size: 21.3333px;">সার্পোট হিসেবে কাজ করবে।</span></h5><h5>&nbsp;</h5><h5><span style="font-family: Wingdings; font-size: 21.3333px;">বিভিন্ন সেক্টরে অনুষ্টিত চাকুরি এবং ডুয়েট ভর্তি </span><span style="font-family: Wingdings; font-size: 21.3333px;">পরীক্ষার </span><span style="font-family: Wingdings; font-size: 21.3333px; text-indent: -0.25in;">প্রশ্নের ভিডিও ক্লাসসহ সমাধান করা।</span></h5><h5>&nbsp;</h5><h5><span style="font-family: Wingdings; font-size: 21.3333px;">প্রতি সপ্তাহে ইইই স্মার্টবুক এর সাথে যুক্ত সকল শিক্ষার্থীদের সাথে সরাসরি লাইভ ক্লসের ব্যবস্থা।</span></h5><h5 class="MsoListParagraphCxSpFirst" style="text-indent: -0.25in;">&nbsp;</h5><h5><span style="color: #ff0000;"><strong><span style="font-family: Wingdings; font-size: 21.3333px;">তাহলে আর দেরি না করে এখনই বইটি অর্ডার করুন। প্রথমে প্রদত্ত যেকোনো অ্যাকাউন্ট নম্বরে বইয়ের মূল্য ৪৫০ টাকা এবং ৫০ টাকা কুরিয়ার খরচ সহ মোট ৫০০ টাকা পাঠিয়ে মানি ট্রান্সফার সংখ্যা সহ অনান্য তথ্য দিয়ে পাশের অপশনগুলো পুরণ করে সাবমিট করুন।</span></strong></span></h5><h5 class="MsoListParagraphCxSpFirst" style="text-indent: -0.25in;"><span style="font-family: SutonnyMJ; font-size: 16pt; text-indent: -0.25in;">c&Ouml;</span></h5><p class="MsoListParagraphCxSpFirst" style="text-indent: -0.25in;">&nbsp;</p>
        var discount_type: String, // none
        var discount_value: Int, // 0
        var id: Int, // 5
        var name: String, // Power System & Telecommunication
        var order: Int, // 1
        var price: Int, // 500
        var slug: String, // power-system-and-telecommunication
        var total_sale: Int, // 55
        var updated_at: String // 2021-12-04T15:09:04.000000Z
    ) {
        data class ChapterDetail(
            var id: Int, // 5
            var name: String, // 1st Chapter: Electricity Generation
            var topic: List<Topic>
        ) {
            data class Topic(
                var avatar: String, // https://engineeringsmartbook.com/storage/topics/8JM2Qj3TSD4QU2z1ysuQBHmPS0XY367G5oXCYu50.png
                var description: String, // <p>Here we discuss the basic operating principle of a steam power plant by a 3-d animitted steam power plant block diagram. Also we discuss 2 important math with basic.</p>
                var id: Int, // 5
                var name: String // Steam Power Plant ( Basic discussion + Problem solution)
            )
        }
    }
}