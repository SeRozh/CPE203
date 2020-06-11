import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public int hashCode(){
      int hash = 1;
      if(prefix != null){ hash = hash * 31 + prefix.hashCode(); }
      if(number != null){ hash = hash * 31 + number.hashCode(); }
      hash = hash * 31 + enrollment;
      if(startTime != null){ hash = hash * 31 + startTime.hashCode(); }
      if(endTime != null) { hash = hash * 31 + endTime.hashCode(); }

      return hash;
   }

   public boolean equals(Object other) {
      if (other != null)
      {
         if (getClass() == other.getClass())
         {
            String otherPrefix = ((CourseSection) other).prefix;
            String otherNumber = ((CourseSection) other).number;
            int otherEnrollment = ((CourseSection) other).enrollment;
            LocalTime otherStart = ((CourseSection) other).startTime;
            LocalTime otherEnd = ((CourseSection) other).endTime;

            boolean prefixEq, numberEq, enrollmentEq, startTimeEq, endTimeEq;

            if (prefix == null) {
               prefixEq = (otherPrefix == null);
            }
            else {
               prefixEq = (prefix.equals(otherPrefix));
            }

            if (number == null) {
               numberEq = (otherNumber == null);
            }
            else {
               numberEq = (number.equals(otherNumber));
            }

            if (startTime == null) {
               startTimeEq = (otherStart == null);
            }
            else {
               startTimeEq = (startTime.equals(otherStart));
            }

            if (endTime == null) {
               endTimeEq = (otherEnd == null);
            }
            else {
               endTimeEq = (endTime.equals(otherEnd));
            }

            enrollmentEq = (enrollment == otherEnrollment);

            return prefixEq && numberEq && enrollmentEq && startTimeEq && endTimeEq;
         }
      }
      return false;
   }
}
